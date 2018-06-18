package cn.edu.gdou.szxhcl.controller;

import cn.edu.gdou.szxhcl.config.UploadImgConfig;
import cn.edu.gdou.szxhcl.exception.FileHandlerException;
import cn.edu.gdou.szxhcl.model.User;
import cn.edu.gdou.szxhcl.model.vo.Response;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleClassVo;
import cn.edu.gdou.szxhcl.model.vo.slideshow.SlideshowVo;
import cn.edu.gdou.szxhcl.service.ArticleService;
import cn.edu.gdou.szxhcl.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {
//    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ArticleService articleService;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public List<ArticleClassVo> getArticleClassList() {
        return articleService.getAllArticleClassList();
    }

    @ModelAttribute
    public User getCurrUser(HttpServletRequest request){
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");

        if(securityContextImpl == null) return null;
        String username = securityContextImpl.getAuthentication().getName();
        return userService.getUserByUsername(username);
    }

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

}
