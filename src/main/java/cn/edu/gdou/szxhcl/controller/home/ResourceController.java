package cn.edu.gdou.szxhcl.controller.home;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleClassVo;
import cn.edu.gdou.szxhcl.model.vo.resource.ResClassVo;
import cn.edu.gdou.szxhcl.model.vo.resource.ResQueryVo;
import cn.edu.gdou.szxhcl.model.vo.resource.ResourceVo;
import cn.edu.gdou.szxhcl.service.ArticleService;
import cn.edu.gdou.szxhcl.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("resource")
public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ArticleService articleService;

    @ModelAttribute
    public List<ArticleClassVo> getArticleClassList() {
        return articleService.getAllArticleClassList();
    }

    @GetMapping("")
    public String resource(ModelMap model, ResQueryVo queryVo, @ModelAttribute List<ArticleClassVo> articleClassVoList){
        List<ResourceVo> resourceList = resourceService.getResourceList(queryVo);
        List<ResClassVo> resClassVoList = resourceService.getResClassList();

        model.put("classList", articleClassVoList);
        model.put("resClassList", resClassVoList);
        model.put("model", queryVo);
        model.put("resList", resourceList);
        model.put("type", "resource");
        return view("home/resource");
    }

    @GetMapping("preview/{id}")
    public String preview(ModelMap model, @PathVariable("id") String id, @ModelAttribute List<ArticleClassVo> articleClassVoList){
        ResourceVo resourceVo = resourceService.getResource(id);

        ResQueryVo queryVo = new ResQueryVo();
        queryVo.setClassId(resourceVo.getClassId());
        List<ResourceVo> resourceList = resourceService.getResourceList(queryVo);
        List<ResClassVo> resClassVoList = resourceService.getResClassList();

        resourceService.addDownNum(id);

        model.put("classList", articleClassVoList);
        model.put("resClassList", resClassVoList);
        model.put("model", resourceVo);
        model.put("resList", resourceList);
        model.put("type", "resource");
        return view("home/resource-detail");
    }
}
