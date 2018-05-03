package cn.edu.gdou.szxhcl.service;

import cn.edu.gdou.szxhcl.model.News;
import cn.edu.gdou.szxhcl.model.vo.news.NewsClassVo;
import cn.edu.gdou.szxhcl.model.vo.news.NewsQueryVo;
import cn.edu.gdou.szxhcl.model.vo.news.NewsVo;

import java.util.List;

public interface NewsService {
    List<NewsVo> getNewsList(NewsQueryVo queryVo);
    NewsVo getNews(String id);
    Boolean isNewsTitleExisted(String id, String title);
    NewsVo saveNews(NewsVo newsVo);
    void deleteNews(String... ids);
    void sortNews(String id);

    List<NewsClassVo> getAllNewsClassList();
    NewsClassVo getNewsClass(String id);
    Boolean isNewsClassTitleExisted(String id, String className);
    NewsClassVo saveNewsClass(NewsClassVo newsClassVo);
    void deleteNewsClass(String... ids);
    Boolean hasNewsInNewsClass(String id);
    void sortNewsClass(String id);
}
