package cn.edu.gdou.szxhcl.controller.admin;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.vo.Introduces.IntroducesVo;
import cn.edu.gdou.szxhcl.service.IntroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("admin/intro")
@Secured("ROLE_ADMIN")
public class AdminIntroController extends BaseController {

    @Autowired
    private IntroService introService;

    @GetMapping("edit")
    public String edit(ModelMap model){
        IntroducesVo introducesVo = introService.getFirst();
        if(introducesVo == null) {
            introducesVo = new IntroducesVo();
        }
        model.put("model", introducesVo);
        return view("admin/intro/edit");
    }

    @PostMapping("edit")
    public String editSubmit(ModelMap model, @Valid IntroducesVo introducesVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", introducesVo);
            return view("admin/intro/edit");
        }

        introService.save(introducesVo);
        return redirectTo("/admin/intro/edit");
    }
}
