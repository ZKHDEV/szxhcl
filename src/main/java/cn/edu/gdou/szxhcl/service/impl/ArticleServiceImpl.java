package cn.edu.gdou.szxhcl.service.impl;

import cn.edu.gdou.szxhcl.dao.ArticleClassDao;
import cn.edu.gdou.szxhcl.dao.ArticleDao;
import cn.edu.gdou.szxhcl.model.Article;
import cn.edu.gdou.szxhcl.model.ArticleClass;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleClassVo;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleQueryVo;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleVo;
import cn.edu.gdou.szxhcl.service.ArticleService;
import cn.edu.gdou.szxhcl.utils.DateTimeUtil;
import cn.edu.gdou.szxhcl.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleClassDao articleClassDao;

    private ArticleVo parseArticle(Article article, Boolean withContent){
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        articleVo.setTitle(article.getTitle());
        articleVo.setAuthor(article.getAuthor());
        articleVo.setCreateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, article.getCreateDt()));
        articleVo.setUpdateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, article.getUpdateDt()));
        articleVo.setSortDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, article.getSortDt()));
        articleVo.setClassName(article.getArticleClass().getClassName());
        articleVo.setClassId(article.getArticleClass().getId());

        if(withContent){
            articleVo.setContent(article.getContent());
        }

        return articleVo;
    }

    private List<ArticleVo> parseArticleList(List<Article> articleList) {
        List<ArticleVo> articleVoList = null;
        if(articleList != null && articleList.size() > 0){
            articleVoList = new ArrayList<>();
            ArticleVo articleVo = null;
            for(Article article : articleList){
                articleVo = parseArticle(article, false);
                articleVoList.add(articleVo);
            }
        }

        return articleVoList;
    }

    private ArticleClassVo parseArticleClass(ArticleClass articleClass){
        ArticleClassVo articleClassVo = new ArticleClassVo();
        articleClassVo.setId(articleClass.getId());
        articleClassVo.setClassName(articleClass.getClassName());
        articleClassVo.setSortDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, articleClass.getSortDt()));

        List<ArticleVo> articleVoList = null;
        List<Article> articleList = articleClass.getArticleList();
        if(articleList != null && articleList.size() > 0) {
            articleVoList = new ArrayList<>();
            for(Article article : articleList) {
                articleVoList.add(parseArticle(article,false));
            }
            Collections.sort(articleVoList);
            articleClassVo.setArticleList(articleVoList);
        }
        
        
        return articleClassVo;
    }

    private List<ArticleClassVo> parseArticleClassList(List<ArticleClass> articleClassList){
        List<ArticleClassVo> articleClassVoList = null;

        if(articleClassList != null && articleClassList.size() > 0){
            articleClassVoList = new ArrayList<>();
            for(ArticleClass articleClass : articleClassList){
                articleClassVoList.add(parseArticleClass(articleClass));
            }
        }

        return articleClassVoList;
    }

    @Override
    public List<ArticleVo> getArticleList(ArticleQueryVo queryVo) {
        List<Article> articleList = articleDao.findAll(new Specification<Article>(){
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList();

                if(!StringUtils.isEmpty(queryVo.getClassId())){
                    predicates.add(criteriaBuilder.equal(root.join("articleClass").get("id"), queryVo.getClassId()));
                }

                predicates.add(criteriaBuilder.like(root.get("title"), StringUtil.surround(queryVo.getTitle(), "%")));
                predicates.add(criteriaBuilder.like(root.get("author"), StringUtil.surround(queryVo.getAuthor(), "%")));

                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
                return null;
            }
        });

        return parseArticleList(articleList);
    }

    @Override
    public ArticleVo getArticle(String id) {
        Article article = articleDao.findFirstById(id);

        ArticleVo articleVo = null;
        if(article != null){
            articleVo = parseArticle(article, true);
        }

        return articleVo;
    }
    @Override
    public ArticleVo getFirstArticleByClassId(String classId){
        Article article = articleDao.findFirstByArticleClass_IdOrderBySortDtDesc(classId);

        ArticleVo articleVo = null;
        if(article != null){
            articleVo = parseArticle(article, true);
        }

        return articleVo;
    }

    @Override
    public Boolean isArticleTitleExisted(String id, String title) {
        return articleDao.countAllByTitleAndIdNot(title, id) > 0;
    }

    @Override
    public ArticleVo saveArticle(ArticleVo articleVo) {
        Article article = null;
        String articleId = articleVo.getId();
        if(!StringUtils.isEmpty(articleId)){
            article = articleDao.findFirstById(articleId);
        }

        if(article == null){
            article = new Article();
            article.setCreateDt(new Date());
            article.setSortDt(new Date());
        }

        ArticleClass articleClass = articleClassDao.findFirstById(articleVo.getClassId());

        article.setTitle(articleVo.getTitle());
        article.setAuthor(articleVo.getAuthor());
        article.setContent(articleVo.getContent());
        article.setUpdateDt(new Date());
        article.setArticleClass(articleClass);

        articleDao.save(article);

        return parseArticle(article,true);
    }

    @Override
    public void deleteArticle(String... ids) {
        for(String id : ids){
            articleDao.deleteById(id);
        }
    }

    @Override
    public void sortArticle(String id) {
        Article article = articleDao.findFirstById(id);
        article.setSortDt(new Date());
        articleDao.save(article);
    }

    @Override
    public List<ArticleClassVo> getAllArticleClassList() {
        List<ArticleClass> articleClassList = articleClassDao.findAllByIdIsNotNullOrderBySortDtDesc();
            List<ArticleClassVo> articleClassVos = parseArticleClassList(articleClassList);
        return parseArticleClassList(articleClassList);
    }

    @Override
    public ArticleClassVo getArticleClass(String id) {
        ArticleClass articleClass = articleClassDao.findFirstById(id);
        ArticleClassVo articleClassVo = null;

        if(articleClass != null){
            articleClassVo = parseArticleClass(articleClass);
        }

        return articleClassVo;
    }

    @Override
    public Boolean isArticleClassTitleExisted(String id, String className) {
        return articleClassDao.countAllByClassNameAndIdNot(className,id) > 0;
    }

    @Override
    public ArticleClassVo saveArticleClass(ArticleClassVo articleClassVo) {
        ArticleClass articleClass = null;
        String articleClassId = articleClassVo.getId();
        if(!StringUtils.isEmpty(articleClassId)){
            articleClass = articleClassDao.findFirstById(articleClassId);
        }

        if(articleClass == null){
            articleClass = new ArticleClass();
            articleClass.setSortDt(new Date());
        }

        articleClass.setClassName(articleClassVo.getClassName());

        articleClassDao.save(articleClass);

        return parseArticleClass(articleClass);
    }

    @Override
    public void deleteArticleClass(String... ids) {
        for(String id : ids){
            articleClassDao.deleteById(id);
        }
    }

    @Override
    public Boolean hasArticleInArticleClass(String id) {
        Boolean hasArticle = false;
        ArticleClass articleClass = articleClassDao.findFirstById(id);
        List<Article> articleList = articleClass.getArticleList();

        if(articleList != null && articleList.size() > 0){
            hasArticle = true;
        }

        return hasArticle;
    }

    @Override
    public void sortArticleClass(String id) {
        ArticleClass articleClass = articleClassDao.findFirstById(id);
        articleClass.setSortDt(new Date());
        articleClassDao.save(articleClass);
    }
}
