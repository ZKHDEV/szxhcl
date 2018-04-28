package cn.edu.gdou.szxhcl.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Value("${app.upload.images.local-path}")
    private String imgUploadDir;
    @Value("${app.upload.images.url-path}")
    private String imgUploadUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(imgUploadUrl + "**").addResourceLocations("file:/" + imgUploadDir);
        super.addResourceHandlers(registry);
    }
}
