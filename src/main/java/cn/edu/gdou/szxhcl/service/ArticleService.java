package cn.edu.gdou.szxhcl.service;

import cn.edu.gdou.szxhcl.model.vo.article.ArticleClassVo;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleQueryVo;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleVo;

import java.util.List;

public interface ArticleService {
    List<ArticleVo> getArticleList(ArticleQueryVo queryVo);
    ArticleVo getArticle(String id);
    ArticleVo getFirstArticleByClassId(String classId);
    Boolean isArticleTitleExisted(String id, String title);
    ArticleVo saveArticle(ArticleVo articleVo);
    void deleteArticle(String... ids);
    void sortArticle(String id);

    List<ArticleClassVo> getAllArticleClassList();
    ArticleClassVo getArticleClass(String id);
    Boolean isArticleClassTitleExisted(String id, String className);
    ArticleClassVo saveArticleClass(ArticleClassVo articleClassVo);
    void deleteArticleClass(String... ids);
    Boolean hasArticleInArticleClass(String id);
    void sortArticleClass(String id);
}
