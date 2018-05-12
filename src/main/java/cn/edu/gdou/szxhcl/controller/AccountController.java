package cn.edu.gdou.szxhcl.controller;

import cn.edu.gdou.szxhcl.controller.BaseController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController extends BaseController {
    @GetMapping("login")
    public String login(){
        return view("login");
    }
}
