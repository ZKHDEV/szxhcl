package cn.edu.gdou.szxhcl.controller.admin;

import cn.edu.gdou.szxhcl.controller.UploadController;
import cn.edu.gdou.szxhcl.exception.FileHandlerException;
import cn.edu.gdou.szxhcl.model.vo.resource.ResClassVo;
import cn.edu.gdou.szxhcl.model.vo.resource.ResQueryVo;
import cn.edu.gdou.szxhcl.model.vo.resource.ResourceVo;
import cn.edu.gdou.szxhcl.service.ResourceService;
import cn.edu.gdou.szxhcl.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("admin/resource")
@Secured({"ROLE_ADMIN","ROLE_TEACHER"})
public class AdminResourceController extends UploadController {

    @Autowired
    private ResourceService resourceService;

    @ModelAttribute
    public List<ResClassVo> getResClassVoList(){
        return resourceService.getResClassList();
    }

    @GetMapping("list")
    public String index(ModelMap model, ResQueryVo queryVo){
        List<ResourceVo> resourceList = resourceService.getResourceList(queryVo);
        List<ResClassVo> resClassVoList = resourceService.getResClassList();
        model.put("classList", resClassVoList);
        model.put("query", queryVo);
        model.put("list", resourceList);
        return view("admin/resource/list");
    }

    @PostMapping("upload")
    public String upload(ModelMap model
            , @RequestParam(value="file",required=true) MultipartFile file
            , @ModelAttribute List<ResClassVo> resClassVoList) throws FileHandlerException {

        try {
            String fileUrl = uploadFile(file);
            ResourceVo resourceVo = new ResourceVo();
            resourceVo.setUrl(fileUrl);
            resourceVo.setSize(StringUtil.prettyDataSize(file.getSize()));

            model.put("classList", resClassVoList);
            model.put("model", resourceVo);
            return view("admin/resource/edit");
        } catch (FileHandlerException e) {
            model.put("errors", e.getMessage());
        }

        return view("admin/resource/list");
    }

    @GetMapping("edit/{id}")
    public String edit(ModelMap model, @PathVariable("id") String id
            , @ModelAttribute List<ResClassVo> resClassVoList){
        ResourceVo resourceVo = resourceService.getResource(id);
        model.put("classList", resClassVoList);
        model.put("model", resourceVo);
        return view("admin/resource/edit");
    }

    @PostMapping("edit")
    public String editSubmit(ModelMap model
            , @Valid ResourceVo resourceVo
            , BindingResult bindingResult
            , @ModelAttribute List<ResClassVo> resClassVoList){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", resourceVo);
            model.put("classList", resClassVoList);
            return view("admin/resource/edit");
        }

        resourceService.saveResource(resourceVo);
        return redirectTo("/admin/resource/list");
    }

    @GetMapping("delete/{id}")
    public String delete(ModelMap model, @PathVariable("id") String id){
        resourceService.deleteResource(id);
        return redirectTo("/admin/resource/list");
    }

    @ResponseBody
    @PostMapping("delete_batch")
    public String deleteBatch(@RequestParam(value = "ids[]") String[] ids){
        resourceService.deleteResource(ids);
        return "/admin/resource/list";
    }

    @GetMapping("class/list")
    public String classIndex(ModelMap model){
        List<ResClassVo> resourceClassVoList = resourceService.getResClassList();
        model.put("list", resourceClassVoList);
        return view("admin/resource/class/list");
    }

    @GetMapping("class/edit")
    public String editClass(ModelMap model){
        model.put("model", new ResClassVo());
        return view("admin/resource/class/edit");
    }

    @GetMapping("class/edit/{id}")
    public String editClass(ModelMap model, @PathVariable("id") String id){
        ResClassVo resClassVo = resourceService.getResClass(id);
        model.put("model", resClassVo);
        return view("admin/resource/class/edit");
    }

    @PostMapping("class/edit")
    public String editClassSubmit(ModelMap model, @Valid ResClassVo resClassVo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.put("errors", prettyErrors(bindingResult.getAllErrors()));
            model.put("model", resClassVo);
            return view("admin/resource/class/edit");
        }

        resourceService.saveResClass(resClassVo);
        return redirectTo("/admin/resource/class/list");
    }

    @GetMapping("class/delete/{id}")
    public String deleteClass(ModelMap model, @PathVariable("id") String id){
        resourceService.deleteResClass(id);
        return redirectTo("/admin/resource/class/list");
    }
}
