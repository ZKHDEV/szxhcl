package cn.edu.gdou.szxhcl.controller.home;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.NewsClass;
import cn.edu.gdou.szxhcl.model.vo.Introduces.IntroducesVo;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleClassVo;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleVo;
import cn.edu.gdou.szxhcl.model.vo.link.LinkVo;
import cn.edu.gdou.szxhcl.model.vo.news.NewsClassVo;
import cn.edu.gdou.szxhcl.model.vo.slideshow.SlideshowVo;
import cn.edu.gdou.szxhcl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private SlideshowService slideshowService;
    @Autowired
    private IntroService introService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private LinkService linkService;

    @GetMapping("/")
    public String index(ModelMap model){

        List<SlideshowVo> slideshowVoList = slideshowService.getShowList();
        List<ArticleClassVo> articleClassVoList = articleService.getAllArticleClassList();
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

    @GetMapping("article")
    public String article(ModelMap model){
        List<ArticleClassVo> articleClassVoList = articleService.getAllArticleClassList();
        ArticleVo articleVo = articleService.getFirstArticle();

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

    @GetMapping("article/{id}")
    public String article(ModelMap model, @PathVariable(value = "id") String id){
        List<ArticleClassVo> articleClassVoList = articleService.getAllArticleClassList();
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

}
