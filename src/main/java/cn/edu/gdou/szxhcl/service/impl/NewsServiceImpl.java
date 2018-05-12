package cn.edu.gdou.szxhcl.service.impl;

import cn.edu.gdou.szxhcl.dao.NewsClassDao;
import cn.edu.gdou.szxhcl.dao.NewsDao;
import cn.edu.gdou.szxhcl.model.News;
import cn.edu.gdou.szxhcl.model.NewsClass;
import cn.edu.gdou.szxhcl.model.vo.news.NewsClassVo;
import cn.edu.gdou.szxhcl.model.vo.news.NewsQueryVo;
import cn.edu.gdou.szxhcl.model.vo.news.NewsVo;
import cn.edu.gdou.szxhcl.service.NewsService;
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
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsDao newsDao;
    @Autowired
    private NewsClassDao newsClassDao;

    private NewsVo parseNews(News news, Boolean withContent){
        NewsVo newsVo = new NewsVo();
        newsVo.setId(news.getId());
        newsVo.setTitle(news.getTitle());
        newsVo.setSource(news.getSource());
        newsVo.setAuthor(news.getAuthor());
        newsVo.setCreateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, news.getCreateDt()));
        newsVo.setUpdateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, news.getUpdateDt()));
        newsVo.setSortDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, news.getSortDt()));
        newsVo.setClassName(news.getNewsClass().getClassName());
        newsVo.setClassId(news.getNewsClass().getId());

        if(withContent){
            newsVo.setContent(news.getContent());
        }

        return newsVo;
    }

    private List<NewsVo> parseNewsList(List<News> newsList) {
        List<NewsVo> newsVoList = null;
        if(newsList != null && newsList.size() > 0){
            newsVoList = new ArrayList<>();
            NewsVo newsVo = null;
            for(News news : newsList){
                newsVo = parseNews(news, false);
                newsVoList.add(newsVo);
            }
        }

        return newsVoList;
    }

    private NewsClassVo parseNewsClass(NewsClass newsClass){
        NewsClassVo newsClassVo = new NewsClassVo();
        newsClassVo.setId(newsClass.getId());
        newsClassVo.setClassName(newsClass.getClassName());
        newsClassVo.setBanner(newsClass.getBanner());
        newsClassVo.setSortDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, newsClass.getSortDt()));

        List<NewsVo> newsVoList = null;
        List<News> newsList = newsClass.getNewsList();
        if(newsList != null && newsList.size() > 0) {
            newsVoList = new ArrayList<>();
            for(News news : newsList) {
                newsVoList.add(parseNews(news,false));
            }
            Collections.sort(newsVoList);
            newsClassVo.setNewsList(newsVoList);
        }


        return newsClassVo;
    }

    private List<NewsClassVo> parseNewsClassList(List<NewsClass> newsClassList) {
        List<NewsClassVo> newsClassVoList = null;

        if(newsClassList != null && newsClassList.size() > 0){
            newsClassVoList = new ArrayList<>();
            for(NewsClass newsClass : newsClassList){
                newsClassVoList.add(parseNewsClass(newsClass));
            }
        }

        return newsClassVoList;
    }

    @Override
    public List<NewsVo> getNewsList(NewsQueryVo queryVo) {
        List<News> newsList = newsDao.findAll(new Specification<News>(){
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList();

                if(!StringUtils.isEmpty(queryVo.getClassId())){
                    predicates.add(criteriaBuilder.equal(root.join("newsClass").get("id"), queryVo.getClassId()));
                }

                predicates.add(criteriaBuilder.like(root.get("title"), StringUtil.surround(queryVo.getTitle(), "%")));
                predicates.add(criteriaBuilder.like(root.get("author"), StringUtil.surround(queryVo.getAuthor(), "%")));

                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
                return null;
            }
        });

        return parseNewsList(newsList);
    }

    @Override
    public NewsVo getNews(String id) {
        News news = newsDao.findFirstById(id);

        NewsVo newsVo = null;
        if(news != null){
            newsVo = parseNews(news, true);
        }

        return newsVo;
    }

    @Override
    public NewsVo getFirstNewsByClassId(String classId){
        News news = newsDao.findFirstByNewsClass_IdOrderBySortDtDesc(classId);

        NewsVo newsVo = null;
        if(news != null){
            newsVo = parseNews(news, true);
        }

        return newsVo;
    }

    @Override
    public Boolean isNewsTitleExisted(String id, String title) {
        return newsDao.countAllByTitleAndIdNot(title, id) > 0;
    }

    @Override
    public NewsVo saveNews(NewsVo newsVo) {
        News news = null;
        String newsId = newsVo.getId();
        if(!StringUtils.isEmpty(newsId)){
            news = newsDao.findFirstById(newsId);
        }

        if(news == null){
            news = new News();
            news.setCreateDt(new Date());
            news.setSortDt(new Date());
        }

        NewsClass newsClass = newsClassDao.findFirstById(newsVo.getClassId());

        news.setTitle(newsVo.getTitle());
        news.setAuthor(newsVo.getAuthor());
        news.setSource(newsVo.getSource());
        news.setContent(newsVo.getContent());
        news.setUpdateDt(new Date());
        news.setNewsClass(newsClass);

        newsDao.save(news);

        return parseNews(news,true);
    }

    @Override
    public void deleteNews(String... ids) {
        for(String id : ids){
            newsDao.deleteById(id);
        }
    }

    @Override
    public void sortNews(String id) {
        News news = newsDao.findFirstById(id);
        news.setSortDt(new Date());
        newsDao.save(news);
    }

    @Override
    public List<NewsClassVo> getAllNewsClassList() {
        List<NewsClass> newsClassList = newsClassDao.findAll();

        return parseNewsClassList(newsClassList);
    }

    @Override
    public NewsClassVo getNewsClass(String id) {
        NewsClass newsClass = newsClassDao.findFirstById(id);
        NewsClassVo newsClassVo = null;

        if(newsClass != null){
            newsClassVo = parseNewsClass(newsClass);
        }

        return newsClassVo;
    }

    @Override
    public Boolean isNewsClassTitleExisted(String id, String className) {
        return newsClassDao.countAllByClassNameAndIdNot(className,id) > 0;
    }

    @Override
    public NewsClassVo saveNewsClass(NewsClassVo newsClassVo) {
        NewsClass newsClass = null;
        String newsClassId = newsClassVo.getId();
        if(!StringUtils.isEmpty(newsClassId)){
            newsClass = newsClassDao.findFirstById(newsClassId);
        }

        if(newsClass == null){
            newsClass = new NewsClass();
            newsClass.setSortDt(new Date());
        }

        newsClass.setClassName(newsClassVo.getClassName());
        newsClass.setBanner(newsClassVo.getBanner());

        newsClassDao.save(newsClass);

        return parseNewsClass(newsClass);
    }

    @Override
    public void deleteNewsClass(String... ids) {
        for(String id : ids){
            newsClassDao.deleteById(id);
        }
    }

    @Override
    public Boolean hasNewsInNewsClass(String id) {
        Boolean hasNews = false;
        NewsClass newsClass = newsClassDao.findFirstById(id);
        List<News> newsList = newsClass.getNewsList();

        if(newsList != null && newsList.size() > 0){
            hasNews = true;
        }

        return hasNews;
    }

    @Override
    public void sortNewsClass(String id) {
        NewsClass newsClass = newsClassDao.findFirstById(id);
        newsClass.setSortDt(new Date());
        newsClassDao.save(newsClass);
    }
}
