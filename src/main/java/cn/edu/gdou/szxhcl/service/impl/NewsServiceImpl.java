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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        newsVo.setTop(news.getTop());
        newsVo.setClassName(news.getNewsClass().getClassName());
        newsVo.setClassId(news.getNewsClass().getId());

        if(withContent){
            newsVo.setContent(news.getContent());
        }

        return newsVo;
    }

    private NewsClassVo parseNewsClass(NewsClass newsClass){
        NewsClassVo newsClassVo = new NewsClassVo();
        newsClassVo.setId(newsClass.getId());
        newsClassVo.setClassName(newsClass.getClassName());
        return newsClassVo;
    }

    @Override
    public List<NewsVo> getNewsList(String classId, String title) {
        List<News> newsList = null;
        if(StringUtils.isEmpty(classId)){
            newsList = newsDao.findAllByTitleLike(
                    StringUtil.surround(title,"%")
            );
        } else {
            newsList = newsDao.findAllByTitleLikeAndNewsClass_Id(
                    StringUtil.surround(title,"%"),
                    classId
            );
        }

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
            news.setTop(false);
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
    public void setTopNews(String id, Boolean isTop) {
        News news = newsDao.findFirstById(id);
        if((news.getTop() && isTop)
                || (!news.getTop() && !isTop)){
            return;
        }

        news.setTop(isTop);
        newsDao.save(news);
    }

    @Override
    public List<NewsClassVo> getAllNewsClassList() {
        List<NewsClass> newsClassList = newsClassDao.findAll();
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
        }

        newsClass.setClassName(newsClassVo.getClassName());

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
}
