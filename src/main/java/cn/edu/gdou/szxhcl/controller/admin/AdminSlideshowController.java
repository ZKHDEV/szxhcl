package cn.edu.gdou.szxhcl.controller.admin;

import cn.edu.gdou.szxhcl.controller.UploadController;
import cn.edu.gdou.szxhcl.exception.FileHandlerException;
import cn.edu.gdou.szxhcl.model.vo.slideshow.SlideshowVo;
import cn.edu.gdou.szxhcl.service.SlideshowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("admin/slideshow")
@Secured("ROLE_ADMIN")
public class AdminSlideshowController extends UploadController {

    @Autowired
    private SlideshowService slideshowService;

    @GetMapping("list")
    public String index(ModelMap model){
        List<SlideshowVo> slideshowVoList = slideshowService.getList();
        model.put("list", slideshowVoList);
        return view("admin/slideshow/list");
    }

    @PostMapping("upload")
    public String upload(ModelMap model, @RequestParam(value="file",required=true) MultipartFile file) throws IOException {

        try {
            String imgUrl = uploadImg(file);
            SlideshowVo slideshowVo = new SlideshowVo();
            slideshowVo.setUrl(imgUrl);

            model.put("model", slideshowVo);
            return view("admin/slideshow/edit");
        } catch (FileHandlerException e) {
            model.put("errors", e.getMessage());
        }

        return view("admin/slideshow/list");
    }

    @GetMapping("edit/{id}")
    public String edit(ModelMap model, @PathVariable("id") String id){
        SlideshowVo slideshowVo = slideshowService.getOne(id);
        model.put("model", slideshowVo);
        return view("admin/slideshow/edit");
    }

    @PostMapping("edit")
    public String editSubmit(ModelMap model, @Valid SlideshowVo slideshowVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", slideshowVo);
            return view("admin/slideshow/edit");
        }

        slideshowService.save(slideshowVo);
        return redirectTo("/admin/slideshow/list");
    }

    @GetMapping("delete/{id}")
    public String delete(ModelMap model, @PathVariable("id") String id){
        slideshowService.delete(id);
        return redirectTo("/admin/slideshow/list");
    }

    @ResponseBody
    @PostMapping("delete_batch")
    public String deleteBatch(@RequestParam(value = "ids[]") String[] ids){
        slideshowService.delete(ids);
        return "/admin/slideshow/list";
    }

    @GetMapping("sort/{id}")
    public String sortClass(ModelMap model, @PathVariable("id") String id){
        slideshowService.sort(id);
        return redirectTo("/admin/slideshow/list");
    }

    @GetMapping("show/{id}")
    public String show(ModelMap model, @PathVariable("id") String id){
        slideshowService.changShow(id);
        return redirectTo("/admin/slideshow/list");
    }
}
