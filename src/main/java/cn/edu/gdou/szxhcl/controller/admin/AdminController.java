package cn.edu.gdou.szxhcl.controller.admin;

import cn.edu.gdou.szxhcl.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController extends BaseController {
    @RequestMapping("")
    public String index(ModelMap model){
        return redirectTo("/admin/index");
    }

    @RequestMapping("/index")
    public String home(ModelMap model){
        return view("admin/index");
    }
}
