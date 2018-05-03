package cn.edu.gdou.szxhcl.controller.admin;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.vo.link.LinkVo;
import cn.edu.gdou.szxhcl.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin/link")
public class AdminLinkController extends BaseController {
    @Autowired
    private LinkService linkService;

    @GetMapping("list")
    public String list(ModelMap model){
        List<LinkVo> linkList = linkService.getLinkList();
        model.put("list", linkList);
        return view("admin/link/list");
    }

    @GetMapping("edit")
    public String edit(ModelMap model){
        model.put("model", new LinkVo());
        return view("admin/link/edit");
    }

    @GetMapping("edit/{id}")
    public String edit(ModelMap model, @PathVariable("id") String id){
        LinkVo linkVo = linkService.getLink(id);
        model.put("model", linkVo);
        return view("admin/link/edit");
    }

    @PostMapping("edit")
    public String editSubmit(ModelMap model, @Valid LinkVo linkVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", linkVo);
            return view("admin/link/edit");
        }

        linkService.save(linkVo);
        return redirectTo("/admin/link/list");
    }

    @GetMapping("delete/{id}")
    public String delete(ModelMap model, @PathVariable("id") String id){
        linkService.delete(id);
        return redirectTo("/admin/link/list");
    }
    
}
