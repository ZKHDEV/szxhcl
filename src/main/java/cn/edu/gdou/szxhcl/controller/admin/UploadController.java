package cn.edu.gdou.szxhcl.controller.admin;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.exception.FileHandlerException;
import cn.edu.gdou.szxhcl.model.vo.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("admin/upload")
public class UploadController extends BaseController {

    @PostMapping("image")
    @ResponseBody
    public Response upImg(@RequestParam(value="filedata",required=true) MultipartFile file){
        Response resp = new Response();
        try {
            resp.setMsg(uploadImg(file));
        } catch (FileHandlerException e) {
            resp.setErr(e.getMessage());
        }

        return resp;
    }
}
