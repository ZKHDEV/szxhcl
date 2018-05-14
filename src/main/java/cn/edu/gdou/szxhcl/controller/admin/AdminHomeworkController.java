package cn.edu.gdou.szxhcl.controller.admin;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.User;
import cn.edu.gdou.szxhcl.model.vo.homework.HomeworkQueryVo;
import cn.edu.gdou.szxhcl.model.vo.homework.HomeworkSubmitVo;
import cn.edu.gdou.szxhcl.model.vo.homework.HomeworkVo;
import cn.edu.gdou.szxhcl.model.vo.user.UserVo;
import cn.edu.gdou.szxhcl.service.HomeworkService;
import cn.edu.gdou.szxhcl.service.UserService;
import cn.edu.gdou.szxhcl.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("admin/homework")
@Secured({"ROLE_ADMIN","ROLE_TEACHER"})
public class AdminHomeworkController extends BaseController {
    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public User getCurrUser(HttpServletRequest request){
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");

        String username = securityContextImpl.getAuthentication().getName();
        return userService.getUserByUsername(username);
    }

    @GetMapping("list")
    public String index(ModelMap model, HomeworkQueryVo queryVo, @ModelAttribute User currUser){
        List<HomeworkVo> homeworkVoList = homeworkService.getHomeworkListByUser(queryVo,currUser.getId());
        model.put("list", homeworkVoList);
        return view("admin/homework/list");
    }

    @GetMapping("edit")
    public String edit(ModelMap model){
        model.put("model", new HomeworkVo());
        return view("admin/homework/edit");
    }

    @GetMapping("edit/{id}")
    public String edit(ModelMap model, @PathVariable("id") String id){
        HomeworkVo homeworkVo = homeworkService.getHomework(id,true,false);
        model.put("model", homeworkVo);
        return view("admin/homework/edit");
    }

    @PostMapping("edit")
    public String editSubmit(ModelMap model, HttpServletRequest request, @Valid HomeworkVo homeworkVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", homeworkVo);
            return view("admin/homework/edit");
        }

        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        String username = securityContextImpl.getAuthentication().getName();
        String userId = userService.getUserByUsername(username).getId();

        homeworkService.saveHomework(homeworkVo,userId);
        return redirectTo("/admin/homework/list");
    }

    @GetMapping("submit/{id}")
    public String submit(ModelMap model, @PathVariable("id") String id){
        List<HomeworkSubmitVo> homeworkSubmitVoList = homeworkService.getSubmitList(id);
        model.put("hwId", id);
        model.put("list", homeworkSubmitVoList);
        return view("admin/homework/submit");
    }

    @ResponseBody
    @GetMapping("sore/{hwId}/{id}")
    public String sore(ModelMap model
            , @PathVariable("hwId") String hwId
            , @PathVariable("id") String id
            , @RequestParam("score") Integer score){

        homeworkService.score(id, score);
        return "/admin/homework/submit/" + hwId;
    }

    @GetMapping("delete/{id}")
    public String delete(ModelMap model, @PathVariable("id") String id){
        homeworkService.deleteHomework(id);
        return redirectTo("/admin/homework/list");
    }

    @GetMapping("delete_submit/{hwId}/{id}")
    public String deleteReply(ModelMap model, @PathVariable("hwId") String hwId, @PathVariable("id") String id){
        homeworkService.deleteHomeworkSubmit(id);
        return redirectTo("/admin/homework/submit/" + hwId);
    }

    @ResponseBody
    @PostMapping("delete_batch")
    public String deleteBatch(@RequestParam(value = "ids[]") String[] ids){
        homeworkService.deleteHomework(ids);
        return "/admin/homework/list";
    }

    @ResponseBody
    @PostMapping("delete_submit_batch/{hwId}")
    public String deleteReplyBatch(@RequestParam(value = "ids[]") String[] ids, @PathVariable("hwId") String hwId){
        homeworkService.deleteHomeworkSubmit(ids);
        return "/admin/homework/submit/" + hwId;
    }
}
