package cn.edu.gdou.szxhcl.controller.admin;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.User;
import cn.edu.gdou.szxhcl.model.vo.exam.ChoiceQueryVo;
import cn.edu.gdou.szxhcl.model.vo.exam.ChoiceVo;
import cn.edu.gdou.szxhcl.model.vo.exam.ExamQueryVo;
import cn.edu.gdou.szxhcl.model.vo.exam.ExamVo;
import cn.edu.gdou.szxhcl.service.ExamService;
import cn.edu.gdou.szxhcl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin")
@Secured({"ROLE_ADMIN","ROLE_TEACHER"})
public class AdminExamController extends BaseController {
    @Autowired
    private ExamService examService;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public User getCurrUser(HttpServletRequest request){
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");

        String username = securityContextImpl.getAuthentication().getName();
        return userService.getUserByUsername(username);
    }

    private Boolean isAdmin(User user) {
        return "ROLE_ADMIN".equals(user.getRole());
    }

    @GetMapping("exam/list")
    public String list(ModelMap model, ExamQueryVo queryVo, @ModelAttribute User currUser){
        List<ExamVo> examVoList = examService.getExamList(queryVo, currUser.getId(), isAdmin(currUser));

        model.put("query", queryVo);
        model.put("list", examVoList);
        return view("admin/exam/list");
    }

    @GetMapping("exam/edit")
    public String edit(ModelMap model){
        model.put("model", new ExamVo());
        return view("admin/exam/edit");
    }

    @GetMapping("exam/edit/{examId}")
    public String editExam(ModelMap model, @PathVariable("examId") String examId, @ModelAttribute User currUser){
        ExamVo examVo = examVo = examService.getExam(examId, currUser.getId(), isAdmin(currUser));
        model.put("model", examVo);
        return view("admin/exam/edit");
    }

    @PostMapping("exam/edit")
    public String editExamSubmit(ModelMap model, HttpServletRequest request, @Valid ExamVo examVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", examVo);
            return view("admin/exam/edit");
        }

        User currUser = getCurrUser(request);
        examService.saveExam(examVo, currUser.getId(), isAdmin(currUser));

        return redirectTo("/admin/exam/list");
    }

    @GetMapping("exam/delete/{examId}")
    public String deleteExam(ModelMap model, @PathVariable("examId") String examId, @ModelAttribute User currUser){
        examService.deleteExam(currUser.getId(), isAdmin(currUser), examId);
        return redirectTo("/admin/exam/list");
    }

    @ResponseBody
    @PostMapping("exam/delete_batch")
    public String deleteExamBatch(@RequestParam(value = "ids[]") String[] ids, @ModelAttribute User currUser){
        examService.deleteExam(currUser.getId(), isAdmin(currUser), ids);
        return "/admin/exam/list";
    }

    @GetMapping("choice/list")
    public String choice(ModelMap model, ChoiceQueryVo queryVo, @RequestParam(value = "eid",required = true) String examId, @ModelAttribute User currUser){
        List<ChoiceVo> choiceVoList = examService.getChoiceList(queryVo, examId, currUser.getId(), isAdmin(currUser));
        model.put("examId", examId);
        model.put("query", queryVo);
        model.put("list", choiceVoList);
        return view("admin/exam/choice/list");
    }

    @GetMapping("choice/edit")
    public String editChoice(ModelMap model, @RequestParam(value = "eid",required = true) String examId){
        ChoiceVo choiceVo = new ChoiceVo();
        choiceVo.setExamId(examId);
        model.put("model", choiceVo);
        return view("admin/exam/choice/edit");
    }

    @GetMapping("choice/edit/{choiceId}")
    public String editChoice(ModelMap model, @PathVariable("choiceId") String choiceId, @ModelAttribute User currUser){
        ChoiceVo choiceVo = examService.getChoice(choiceId, currUser.getId(), isAdmin(currUser));
        model.put("model", choiceVo);
        return view("admin/exam/choice/edit");
    }

    @PostMapping("choice/edit")
    public String editChoiceSubmit(ModelMap model, HttpServletRequest request, @Valid ChoiceVo choiceVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", choiceVo);
            return view("admin/exam/choice/edit");
        }

        User currUser = getCurrUser(request);
        examService.saveChoice(choiceVo, currUser.getId(), isAdmin(currUser));

        return redirectTo("/admin/choice/list?eid=" + choiceVo.getExamId());
    }

    @GetMapping("choice/delete/{choiceId}")
    public String deleteChoice(ModelMap model, @RequestParam(value = "eid",required = true) String examId, @PathVariable("choiceId") String choiceId, @ModelAttribute User currUser){
        examService.deleteChoice(currUser.getId(), isAdmin(currUser), choiceId);
        return redirectTo("/admin/choice/list?eid=" + examId);
    }

    @ResponseBody
    @PostMapping("choice/delete_batch")
    public String deleteChoiceBatch(@RequestParam(value = "eid",required = true) String examId, @RequestParam(value = "ids[]") String[] ids, @ModelAttribute User currUser){
        examService.deleteChoice(currUser.getId(), isAdmin(currUser), ids);
        return "/admin/choice/list?eid=" + examId;
    }

    @GetMapping("choice/sort/{choiceId}")
    public String sortChoice(ModelMap model, @RequestParam(value = "eid",required = true) String examId, @PathVariable("choiceId") String choiceId, @ModelAttribute User currUser){
        examService.sortChoice(choiceId, currUser.getId(), isAdmin(currUser));
        return redirectTo("/admin/choice/list?eid=" + examId);
    }
}
