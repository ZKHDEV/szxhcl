package cn.edu.gdou.szxhcl.controller.admin;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.vo.news.NewsClassVo;
import cn.edu.gdou.szxhcl.model.vo.news.NewsVo;
import cn.edu.gdou.szxhcl.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin/news")
public class NewsController extends BaseController {

    @Autowired
    private NewsService newsService;

    @GetMapping("list")
    public String index(ModelMap model, String classId, String title){
        List<NewsVo> newsList = newsService.getNewsList(classId, title);
        List<NewsClassVo> newsClassVoList = newsService.getAllNewsClassList();
        model.put("classList", newsClassVoList);
        model.put("classId", classId);
        model.put("title", title);
        model.put("list", newsList);
        return view("admin/news/list");
    }

    @GetMapping("create")
    public String create(ModelMap model){
        List<NewsClassVo> newsClassVoList = newsService.getAllNewsClassList();
        model.put("classList", newsClassVoList);
        model.put("model", new NewsVo());
        return view("admin/news/create");
    }

    @PostMapping("create")
    public String createSubmit(ModelMap model, @Valid NewsVo newsVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", newsVo);
            List<NewsClassVo> newsClassVoList = newsService.getAllNewsClassList();
            model.put("classList", newsClassVoList);
            return view("admin/news/create");
        }

        newsService.saveNews(newsVo);
        return redirectTo("/admin/news/list");
    }

    @GetMapping("delete/{id}")
    public String delete(ModelMap model, @PathVariable("id") String id){
        newsService.deleteNews(id);
        return redirectTo("/admin/news/list");
    }

    @PostMapping("delete_batch")
    public String deleteBatch(@RequestBody String[] ids){
        newsService.deleteNews(ids);
        return redirectTo("/admin/news/list");
    }

    @GetMapping("update/{id}")
    public String update(ModelMap model, @PathVariable("id") String id){
        NewsVo newsVo = newsService.getNews(id);
        List<NewsClassVo> newsClassVoList = newsService.getAllNewsClassList();
        model.put("classList", newsClassVoList);
        model.put("model", newsVo);
        return view("admin/news/update");
    }

    @PostMapping("update")
    public String updateSubmit(ModelMap model, @Valid NewsVo newsVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", newsVo);
            List<NewsClassVo> newsClassVoList = newsService.getAllNewsClassList();
            model.put("classList", newsClassVoList);
            return view("admin/news/update");
        }

        newsService.saveNews(newsVo);
        return redirectTo("/admin/news/list");
    }

    @GetMapping("add_top/{id}")
    public String addTop(ModelMap model, @PathVariable("id") String id){
        newsService.setTopNews(id, true);
        return redirectTo("/admin/news/list");
    }

    @GetMapping("rm_top/{id}")
    public String rmTop(ModelMap model, @PathVariable("id") String id){
        newsService.setTopNews(id, false);
        return redirectTo("/admin/news/list");
    }

}
