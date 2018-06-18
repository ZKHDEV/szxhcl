package cn.edu.gdou.szxhcl.controller.home;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.vo.Introduces.IntroducesVo;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleClassVo;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleVo;
import cn.edu.gdou.szxhcl.model.vo.link.LinkVo;
import cn.edu.gdou.szxhcl.model.vo.news.NewsClassVo;
import cn.edu.gdou.szxhcl.model.vo.news.NewsVo;
import cn.edu.gdou.szxhcl.model.vo.slideshow.SlideshowVo;
import cn.edu.gdou.szxhcl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private SlideshowService slideshowService;
    @Autowired
    private IntroService introService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private LinkService linkService;

    @GetMapping("")
    public String index(ModelMap model
            ,@ModelAttribute List<ArticleClassVo> articleClassVoList){

        List<SlideshowVo> slideshowVoList = slideshowService.getShowList();
        IntroducesVo introducesVo = introService.getFirst();
        List<NewsClassVo> newsClassVoList = newsService.getAllNewsClassList();
        List<LinkVo> linkVoList = linkService.getLinkList();

        model.put("classList", articleClassVoList);
        model.put("slideshowList",slideshowVoList);
        model.put("introduces",introducesVo.getContent());
        model.put("newsClassList",newsClassVoList);
        model.put("linkList",linkVoList);

        return view("home/index");
    }

    @GetMapping("index")
    public String index(){
        return redirectTo("/");
    }

    @GetMapping("article/{id}")
    public String article(ModelMap model
            , @PathVariable(value = "id") String id
            ,@ModelAttribute List<ArticleClassVo> articleClassVoList){
        ArticleVo articleVo = articleService.getArticle(id);

        if(articleVo == null){
            return notFound();
        }

        ArticleClassVo articleClassVo = articleService.getArticleClass(articleVo.getClassId());

        model.put("classList", articleClassVoList);
        model.put("model", articleVo);
        model.put("modelList", articleClassVo.getArticleList());
        model.put("type", "article");

        return view("home/page");
    }

    @GetMapping("article/class/{id}")
    public String articleClass(ModelMap model
            , @PathVariable(value = "id") String id
            ,@ModelAttribute List<ArticleClassVo> articleClassVoList){
        ArticleVo articleVo = articleService.getFirstArticleByClassId(id);

        if(articleVo == null){
            return notFound();
        }

        ArticleClassVo articleClassVo = articleService.getArticleClass(articleVo.getClassId());

        model.put("classList", articleClassVoList);
        model.put("model", articleVo);
        model.put("modelList", articleClassVo.getArticleList());
        model.put("type", "article");

        return view("home/page");
    }

    @GetMapping("news/{id}")
    public String news(ModelMap model
            , @PathVariable(value = "id") String id
            ,@ModelAttribute List<ArticleClassVo> articleClassVoList){
        NewsVo newsVo = newsService.getNews(id);

        if(newsVo == null){
            return notFound();
        }

        NewsClassVo newsClassVo = newsService.getNewsClass(newsVo.getClassId());

        model.put("classList", articleClassVoList);
        model.put("model", newsVo);
        model.put("modelList", newsClassVo.getNewsList());
        model.put("type", "news");

        return view("home/page");
    }

    @GetMapping("news/class/{id}")
    public String newsClass(ModelMap model
            , @PathVariable(value = "id") String id
            ,@ModelAttribute List<ArticleClassVo> articleClassVoList){

        NewsClassVo newsClassVo = newsService.getNewsClass(id);
        List<NewsClassVo> newsClassVoList = newsService.getAllNewsClassList();

        model.put("classList", articleClassVoList);
        model.put("model", newsClassVo);
        model.put("newsClassList", newsClassVoList);
        model.put("newsList", newsClassVo.getNewsList());
        model.put("type", "news");

        return view("home/news-list");
    }

}
