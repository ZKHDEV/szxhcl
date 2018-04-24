package cn.edu.gdou.szxhcl.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("index")
    public String index(ModelMap model){
        return "admin/news";
    }
}
