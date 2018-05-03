package cn.edu.gdou.szxhcl.controller;

import cn.edu.gdou.szxhcl.config.UploadImgConfig;
import cn.edu.gdou.szxhcl.exception.FileHandlerException;
import cn.edu.gdou.szxhcl.model.vo.Response;
import cn.edu.gdou.szxhcl.model.vo.slideshow.SlideshowVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UploadImgConfig imgConfig;

    protected String view(String view){
        return view;
    }

    protected String redirectTo(String url){
        return "redirect:" + url;
    }

    protected String notFound(){
        return "404";
    }

    /**
     * 转换错误信息为Map
     * @param errors 错误信息List
     * @return
     */
    protected Map<String,String> prettyErrors(List<ObjectError> errors){
        Map<String,String> map = new HashMap<String, String>();
        for(ObjectError error : errors){
            map.put(((FieldError)error).getField(),error.getDefaultMessage());
        }
        return map;
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
}
