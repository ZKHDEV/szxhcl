package cn.edu.gdou.szxhcl.controller.admin;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.Slideshow;
import cn.edu.gdou.szxhcl.model.vo.slideshow.SlideshowVo;
import cn.edu.gdou.szxhcl.service.SlideshowService;
import cn.edu.gdou.szxhcl.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/slideshow")
public class AdminSlideshowController extends BaseController {

    @Value("${app.upload.images.local-path}")
    private String imgUploadDir;
    @Value("${app.upload.images.url-path}")
    private String imgUploadUrl;

    private final static String[] ALLOW_IMG_TYPES = { "JPG","JPEG","PNG","GIF","BMP" };

    @Autowired
    private SlideshowService slideshowService;

    @GetMapping("list")
    public String index(ModelMap model){
        List<SlideshowVo> slideshowVoList = slideshowService.getList();
        model.put("list", slideshowVoList);
        return view("admin/slideshow/list");
    }

    @PostMapping("upload")
    public String upload(ModelMap model, HttpServletRequest request, @RequestParam(value="file",required=true) MultipartFile file) throws IOException {
        if(file != null){
            if(file.getSize() < 1000000L){
                String fileName = file.getOriginalFilename();
                String type = fileName.indexOf(".")!=-1 ? fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()) : null;

                if(!StringUtils.isEmpty(type)
                        && Arrays.asList(ALLOW_IMG_TYPES).contains(type.toUpperCase())){

                    String tarDirUrl = imgUploadDir;
                    File tarDir = new File(tarDirUrl);
                    if(!tarDir.exists()){
                        tarDir.mkdirs();
                    }

                    String tarFileName = String.valueOf(System.currentTimeMillis())+fileName.substring(0,fileName.lastIndexOf("."))+".JPG";
                    String tarFullPath = tarDirUrl + tarFileName;
                    file.transferTo(new File(tarFullPath));

                    SlideshowVo slideshowVo = new SlideshowVo();
                    slideshowVo.setUrl(imgUploadUrl + tarFileName);
                    
                    model.put("model", slideshowVo);
                    return view("admin/slideshow/edit");

                } else {
                    model.put("errors", "文件类型错误！");
                }
            } else {
                model.put("errors", "文件须小于1MB！");
            }
        }else{
            model.put("errors", "文件不能为空！");
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
