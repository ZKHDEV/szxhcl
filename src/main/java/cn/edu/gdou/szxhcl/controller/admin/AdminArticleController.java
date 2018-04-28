package cn.edu.gdou.szxhcl.controller.admin;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleClassVo;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleQueryVo;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleVo;
import cn.edu.gdou.szxhcl.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin/article")
public class AdminArticleController extends BaseController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("list")
    public String index(ModelMap model, ArticleQueryVo queryVo){
        List<ArticleVo> articleList = articleService.getArticleList(queryVo);
        List<ArticleClassVo> articleClassVoList = articleService.getAllArticleClassList();
        model.put("classList", articleClassVoList);
        model.put("query", queryVo);
        model.put("list", articleList);
        return view("admin/article/list");
    }

    @GetMapping("edit")
    public String edit(ModelMap model){
        List<ArticleClassVo> articleClassVoList = articleService.getAllArticleClassList();
        model.put("classList", articleClassVoList);
        model.put("model", new ArticleVo());
        return view("admin/article/edit");
    }

    @GetMapping("edit/{id}")
    public String edit(ModelMap model, @PathVariable("id") String id){
        ArticleVo articleVo = articleService.getArticle(id);
        List<ArticleClassVo> articleClassVoList = articleService.getAllArticleClassList();
        model.put("classList", articleClassVoList);
        model.put("model", articleVo);
        return view("admin/article/edit");
    }

    @PostMapping("edit")
    public String editSubmit(ModelMap model, @Valid ArticleVo articleVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", articleVo);
            List<ArticleClassVo> articleClassVoList = articleService.getAllArticleClassList();
            model.put("classList", articleClassVoList);
            return view("admin/article/edit");
        }

        articleService.saveArticle(articleVo);
        return redirectTo("/admin/article/list");
    }

    @GetMapping("delete/{id}")
    public String delete(ModelMap model, @PathVariable("id") String id){
        articleService.deleteArticle(id);
        return redirectTo("/admin/article/list");
    }

    @ResponseBody
    @PostMapping("delete_batch")
    public String deleteBatch(@RequestParam(value = "ids[]") String[] ids){
        articleService.deleteArticle(ids);
        return "/admin/article/list";
    }

    @GetMapping("sort/{id}")
    public String sort(ModelMap model, @PathVariable("id") String id){
        articleService.sortArticle(id);
        return redirectTo("/admin/article/list");
    }

    @GetMapping("class/list")
    public String classIndex(ModelMap model){
        List<ArticleClassVo> articleClassVoList = articleService.getAllArticleClassList();
        model.put("list", articleClassVoList);
        return view("admin/article/class/list");
    }

    @GetMapping("class/edit")
    public String editClass(ModelMap model){
        model.put("model", new ArticleClassVo());
        return view("admin/article/class/edit");
    }

    @GetMapping("class/edit/{id}")
    public String editClass(ModelMap model, @PathVariable("id") String id){
        ArticleClassVo articleClassVo = articleService.getArticleClass(id);
        model.put("model", articleClassVo);
        return view("admin/article/class/edit");
    }

    @PostMapping("class/edit")
    public String editClassSubmit(ModelMap model, @Valid ArticleClassVo articleClassVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", articleClassVo);
            return view("admin/article/class/edit");
        }

        articleService.saveArticleClass(articleClassVo);
        return redirectTo("/admin/article/class/list");
    }

    @GetMapping("class/delete/{id}")
    public String deleteClass(ModelMap model, @PathVariable("id") String id){
        articleService.deleteArticleClass(id);
        return redirectTo("/admin/article/class/list");
    }

    @GetMapping("class/sort/{id}")
    public String sortClass(ModelMap model, @PathVariable("id") String id){
        articleService.sortArticleClass(id);
        return redirectTo("/admin/article/class/list");
    }
}
