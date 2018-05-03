package cn.edu.gdou.szxhcl.controller.admin;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.vo.comment.CommentVo;
import cn.edu.gdou.szxhcl.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/comment")
public class AdminCommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @GetMapping("list")
    public String index(ModelMap model){
        List<CommentVo> commentVoList = commentService.getCommentList();
        model.put("list", commentVoList);
        return view("admin/comment/list");
    }

    @GetMapping("delete/{id}")
    public String delete(ModelMap model, @PathVariable("id") String id){
        commentService.deleteComment(id);
        return redirectTo("/admin/comment/list");
    }

    @ResponseBody
    @PostMapping("delete_batch")
    public String deleteBatch(@RequestParam(value = "ids[]") String[] ids){
        commentService.deleteComment(ids);
        return "/admin/comment/list";
    }
}
