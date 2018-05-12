package cn.edu.gdou.szxhcl.controller.admin;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.vo.news.NewsClassVo;
import cn.edu.gdou.szxhcl.model.vo.news.NewsQueryVo;
import cn.edu.gdou.szxhcl.model.vo.news.NewsVo;
import cn.edu.gdou.szxhcl.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/news")
@Secured("ROLE_ADMIN")
public class AdminNewsController extends BaseController {

    @Autowired
    private NewsService newsService;

    @GetMapping("list")
    public String index(ModelMap model, NewsQueryVo queryVo){
        List<NewsVo> newsList = newsService.getNewsList(queryVo);
        List<NewsClassVo> newsClassVoList = newsService.getAllNewsClassList();
        model.put("classList", newsClassVoList);
        model.put("query", queryVo);
        model.put("list", newsList);
        return view("admin/news/list");
    }

    @GetMapping("edit")
    public String edit(ModelMap model){
        List<NewsClassVo> newsClassVoList = newsService.getAllNewsClassList();
        model.put("classList", newsClassVoList);
        model.put("model", new NewsVo());
        return view("admin/news/edit");
    }

    @GetMapping("edit/{id}")
    public String edit(ModelMap model, @PathVariable("id") String id){
        NewsVo newsVo = newsService.getNews(id);
        List<NewsClassVo> newsClassVoList = newsService.getAllNewsClassList();
        model.put("classList", newsClassVoList);
        model.put("model", newsVo);
        return view("admin/news/edit");
    }

    @PostMapping("edit")
    public String editSubmit(ModelMap model, @Valid NewsVo newsVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", newsVo);
            List<NewsClassVo> newsClassVoList = newsService.getAllNewsClassList();
            model.put("classList", newsClassVoList);
            return view("admin/news/edit");
        }

        newsService.saveNews(newsVo);
        return redirectTo("/admin/news/list");
    }

    @GetMapping("delete/{id}")
    public String delete(ModelMap model, @PathVariable("id") String id){
        newsService.deleteNews(id);
        return redirectTo("/admin/news/list");
    }

    @ResponseBody
    @PostMapping("delete_batch")
    public String deleteBatch(@RequestParam(value = "ids[]") String[] ids){
        newsService.deleteNews(ids);
        return "/admin/news/list";
    }

    @GetMapping("sort/{id}")
    public String sort(ModelMap model, @PathVariable("id") String id){
        newsService.sortNews(id);
        return redirectTo("/admin/news/list");
    }

    @GetMapping("class/list")
    public String classIndex(ModelMap model){
        List<NewsClassVo> newsClassVoList = newsService.getAllNewsClassList();
        model.put("list", newsClassVoList);
        return view("admin/news/class/list");
    }

    @GetMapping("class/edit")
    public String editClass(ModelMap model){
        model.put("model", new NewsClassVo());
        return view("admin/news/class/edit");
    }

    @GetMapping("class/edit/{id}")
    public String editClass(ModelMap model, @PathVariable("id") String id){
        NewsClassVo newsClassVo = newsService.getNewsClass(id);
        model.put("model", newsClassVo);
        return view("admin/news/class/edit");
    }

    @PostMapping("class/edit")
    public String editClassSubmit(ModelMap model, @Valid NewsClassVo newsClassVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", newsClassVo);
            return view("admin/news/class/edit");
        }

        newsService.saveNewsClass(newsClassVo);
        return redirectTo("/admin/news/class/list");
    }

    @GetMapping("class/delete/{id}")
    public String deleteClass(ModelMap model, @PathVariable("id") String id){
        newsService.deleteNewsClass(id);
        return redirectTo("/admin/news/class/list");
    }

    @GetMapping("class/update/{id}")
    public String updateClass(ModelMap model, @PathVariable("id") String id){
        NewsClassVo newsClassVo = newsService.getNewsClass(id);
        model.put("model", newsClassVo);
        return view("admin/news/class/update");
    }

    @PostMapping("class/update")
    public String updateClassSubmit(ModelMap model, @Valid NewsClassVo newsClassVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", newsClassVo);
            return view("admin/news/class/update");
        }

        newsService.saveNewsClass(newsClassVo);
        return redirectTo("/admin/news/class/list");
    }

    @GetMapping("class/sort/{id}")
    public String sortClass(ModelMap model, @PathVariable("id") String id){
        newsService.sortNewsClass(id);
        return redirectTo("/admin/news/class/list");
    }

}
