package cn.edu.gdou.szxhcl.controller.home;

import cn.edu.gdou.szxhcl.controller.UploadController;
import cn.edu.gdou.szxhcl.exception.FileHandlerException;
import cn.edu.gdou.szxhcl.model.User;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleClassVo;
import cn.edu.gdou.szxhcl.model.vo.homework.HomeworkSubmitVo;
import cn.edu.gdou.szxhcl.model.vo.homework.HomeworkVo;
import cn.edu.gdou.szxhcl.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("homework")
public class HomeworkController extends UploadController {
    @Autowired
    private HomeworkService homeworkService;

    @GetMapping("")
    public String homework(ModelMap model
            , @ModelAttribute List<ArticleClassVo> articleClassVoList
            , @ModelAttribute User currUser){

        List<HomeworkVo> homeworkVoList1 = homeworkService.getHwListWithSubmitByUser(currUser.getId());
        List<HomeworkVo> homeworkVoList2 = homeworkService.getHwListExceptSubmitByUser(currUser.getId());

        model.put("classList", articleClassVoList);
        model.put("homeworkList1", homeworkVoList1);
        model.put("homeworkList2", homeworkVoList2);
        model.put("type", "homework");
        return view("home/homework");
    }

    @GetMapping("{id}")
    public String homework(ModelMap model
            , @PathVariable("id") String id
            , @ModelAttribute List<ArticleClassVo> articleClassVoList
            , @ModelAttribute User currUser){

        HomeworkVo homeworkVo = homeworkService.getHomework(id, true, false);

        model.put("classList", articleClassVoList);
        model.put("model", homeworkVo);
        model.put("type", "homework");
        return view("home/homework-detail");
    }

    @PostMapping("upload/{hwid}")
    public String upload(ModelMap model
            , @PathVariable("hwid") String hwid
            , @RequestParam(value="file",required=true) MultipartFile file
            , @ModelAttribute List<ArticleClassVo> articleClassVoList
            , @ModelAttribute User currUser) throws FileHandlerException {

        try {
            String fileUrl = uploadFile(file);

            HomeworkSubmitVo hwSubmit = new HomeworkSubmitVo();
            hwSubmit.setUrl(fileUrl);

            HomeworkSubmitVo homeworkSubmitVo = homeworkService.saveHomeworkSubmit(hwSubmit, hwid, currUser.getId());
            if (homeworkSubmitVo == null) {
                model.put("errors", "作业已截止");
            } else {
                return redirectTo("/homework/");
            }
        } catch (FileHandlerException e) {
            model.put("errors", e.getMessage());
        }

        HomeworkVo homeworkVo = homeworkService.getHomework(hwid, true, false);
        model.put("classList", articleClassVoList);
        model.put("model", homeworkVo);
        return view("home/homework-detail");
    }

}
