package cn.edu.gdou.szxhcl.controller;

import cn.edu.gdou.szxhcl.config.UploadFileConfig;
import cn.edu.gdou.szxhcl.config.UploadImgConfig;
import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.exception.FileHandlerException;
import cn.edu.gdou.szxhcl.model.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("admin/upload")
public class UploadController extends BaseController {

    @Autowired
    private UploadImgConfig imgConfig;

    @Autowired
    private UploadFileConfig fileConfig;

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

    @PostMapping("file")
    @ResponseBody
    public Response upFile(@RequestParam(value="filedata",required=true) MultipartFile file){
        Response resp = new Response();
        try {
            resp.setMsg(uploadFile(file));
        } catch (FileHandlerException e) {
            resp.setErr(e.getMessage());
        }

        return resp;
    }

    protected String uploadImg(MultipartFile file) throws FileHandlerException {

        if(file != null){
            if(file.getSize() < imgConfig.getMaxSize()){
                String fileName = file.getOriginalFilename();
                String type = fileName.indexOf(".")!=-1 ? fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()) : null;

                if(!StringUtils.isEmpty(type)
                        && imgConfig.getAllowTypes().toUpperCase().indexOf(type.toUpperCase()) != -1){

                    String tarDirUrl = imgConfig.getLocalPath();
                    File tarDir = new File(tarDirUrl);

                    if(!tarDir.exists()){
                        tarDir.mkdirs();
                    }

                    String tarFileName = imgConfig.getFileNameTemplate()
                            .replaceAll("\\$TIMESTAMP\\$", String.valueOf(System.currentTimeMillis()))
                            .replaceAll("\\$FILENAME\\$", fileName);
                    String tarFullPath = tarDirUrl + tarFileName;
                    try {
                        file.transferTo(new File(tarFullPath));
                    } catch (IOException e) {
                        throw new FileHandlerException(e);
                    }

                    return imgConfig.getUrlPath() + tarFileName;

                } else {
                    throw new FileHandlerException("文件类型错误！");
                }
            } else {
                throw new FileHandlerException("文件须小于" + imgConfig.getMaxSize()/1000 + "KB！");
            }
        }else{
            throw new FileHandlerException("文件不能为空！");
        }
    }

    protected String uploadFile(MultipartFile file) throws FileHandlerException {

        if(file != null){
            if(file.getSize() < fileConfig.getMaxSize()){
                String fileName = file.getOriginalFilename();

                String tarDirUrl = fileConfig.getLocalPath();
                File tarDir = new File(tarDirUrl);

                if(!tarDir.exists()){
                    tarDir.mkdirs();
                }

                String tarFileName = fileConfig.getFileNameTemplate()
                        .replaceAll("\\$TIMESTAMP\\$", String.valueOf(System.currentTimeMillis()))
                        .replaceAll("\\$FILENAME\\$", fileName);
                String tarFullPath = tarDirUrl + tarFileName;
                try {
                    file.transferTo(new File(tarFullPath));
                } catch (IOException e) {
                    throw new FileHandlerException(e);
                }

                return fileConfig.getUrlPath() + tarFileName;

            } else {
                throw new FileHandlerException("文件须小于" + fileConfig.getMaxSize()/1000 + "KB！");
            }
        }else{
            throw new FileHandlerException("文件不能为空！");
        }
    }
}
