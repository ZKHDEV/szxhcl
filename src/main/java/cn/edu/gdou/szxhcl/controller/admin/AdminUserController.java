package cn.edu.gdou.szxhcl.controller.admin;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.vo.user.UserPsdVo;
import cn.edu.gdou.szxhcl.model.vo.user.UserQueryVo;
import cn.edu.gdou.szxhcl.model.vo.user.UserVo;
import cn.edu.gdou.szxhcl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/user")
@Secured("ROLE_ADMIN")
public class AdminUserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("list")
    public String index(ModelMap model, UserQueryVo queryVo) {
        List<UserVo> userVoList = userService.getUserList(queryVo);
        model.put("query", queryVo);
        model.put("list", userVoList);
        return view("admin/user/list");
    }

    @GetMapping("edit")
    public String edit(ModelMap model){
        model.put("model", new UserVo());
        return view("admin/user/edit");
    }

    @GetMapping("edit/{id}")
    public String edit(ModelMap model, @PathVariable("id") String id){
        UserVo userVo = userService.getUser(id);
        model.put("model", userVo);
        return view("admin/user/edit");
    }

    @PostMapping("edit")
    public String editSubmit(ModelMap model, @Valid UserVo userVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", userVo);
            return view("admin/user/edit");
        }

        if(userService.isUsernameExisted(userVo.getId(),userVo.getUsername())) {
            Map<String,String> errorMap = new HashMap<>();
            errorMap.put("username", "用户名或学号已存在");
            model.put("errors", errorMap);
            model.put("model", userVo);
            return view("admin/user/edit");
        }

        userService.save(userVo);
        return redirectTo("/admin/user/list");
    }

    @GetMapping("delete/{id}")
    public String delete(ModelMap model, @PathVariable("id") String id){
        userService.delete(id);
        return redirectTo("/admin/user/list");
    }

    @ResponseBody
    @PostMapping("delete_batch")
    public String deleteBatch(@RequestParam(value = "ids[]") String[] ids){
        userService.delete(ids);
        return "/admin/user/list";
    }

    @GetMapping("set_psd/{id}")
    public String setPsd(ModelMap model, @PathVariable("id") String id) {
        UserVo userVo = userService.getUser(id);
        UserPsdVo userPsdVo = new UserPsdVo();
        userPsdVo.setId(userVo.getId());
        model.put("model", userPsdVo);
        return view("admin/user/set-psd");
    }

    @PostMapping("set_psd")
    public String setPsdSubmit(ModelMap model, @Valid UserPsdVo userPsdVo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", userPsdVo);
            return view("admin/user/set-psd");
        }

        userService.changePsd(userPsdVo.getId(),userPsdVo.getPassword());
        return redirectTo("/admin/user/list");
    }
}
