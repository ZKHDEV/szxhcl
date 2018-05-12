package cn.edu.gdou.szxhcl.controller.home;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.Comment;
import cn.edu.gdou.szxhcl.model.NewsClass;
import cn.edu.gdou.szxhcl.model.vo.Introduces.IntroducesVo;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleClassVo;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleVo;
import cn.edu.gdou.szxhcl.model.vo.comment.CommentVo;
import cn.edu.gdou.szxhcl.model.vo.link.LinkVo;
import cn.edu.gdou.szxhcl.model.vo.news.NewsClassVo;
import cn.edu.gdou.szxhcl.model.vo.news.NewsVo;
import cn.edu.gdou.szxhcl.model.vo.slideshow.SlideshowVo;
import cn.edu.gdou.szxhcl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @Autowired
    private CommentService commentService;

    @ModelAttribute
    public List<ArticleClassVo> getArticleClassList() {
        return articleService.getAllArticleClassList();
    }

    @GetMapping("/")
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

    @GetMapping("comment")
    public String comment(ModelMap model
            ,@ModelAttribute List<ArticleClassVo> articleClassVoList){
        List<CommentVo> commentVoList = commentService.getTopCommentList(false);

        model.put("classList", articleClassVoList);
        model.put("commentList", commentVoList);
        model.put("model", new CommentVo());
        model.put("type", "comment");

        return view("home/comment");
    }

    @GetMapping("comment/{id}")
    public String comment(ModelMap model
            , @PathVariable(value = "id") String id
            ,@ModelAttribute List<ArticleClassVo> articleClassVoList){

        CommentVo commentVo = commentService.getComment(id);
        if(commentVo == null){
            return notFound();
        }

        model.put("classList", articleClassVoList);
        model.put("comment", commentVo);
        model.put("model", new CommentVo());
        model.put("type", "comment");

        return view("home/comment-detail");
    }

    @PostMapping("comment")
    public String comment(ModelMap model
            , @Valid CommentVo commentVo
            , BindingResult bindingResult
            ,@ModelAttribute List<ArticleClassVo> articleClassVoList){
        List<CommentVo> commentVoList = commentService.getTopCommentList(false);

        model.put("classList", articleClassVoList);
        model.put("type", "comment");

        if(bindingResult.hasErrors()){
            model.put("commentList", commentVoList);
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", commentVo);

            return view("home/comment");
        }

        commentVo.setTop(true);
        commentService.save(null,commentVo);

        return redirectTo("/comment");
    }

    @PostMapping("comment/{id}")
    public String comment(ModelMap model
            ,@PathVariable(value = "id") String id
            , @Valid CommentVo commentVo
            , BindingResult bindingResult
            ,@ModelAttribute List<ArticleClassVo> articleClassVoList){

        CommentVo parCommentVo = commentService.getComment(id);
        if(parCommentVo == null){
            return notFound();
        }

        model.put("classList", articleClassVoList);
        model.put("comment", parCommentVo);
        model.put("type", "comment");

        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", commentVo);

            return view("home/comment-detail");
        }

        commentVo.setTop(false);
        commentService.save(id,commentVo);

        return redirectTo("/comment/" + id);
    }

}
