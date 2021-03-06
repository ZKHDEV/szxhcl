package cn.edu.gdou.szxhcl.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Value("${app.upload.image.local-path}")
    private String imgUploadDir;
    @Value("${app.upload.image.url-path}")
    private String imgUploadUrl;
    @Value("${app.upload.file.local-path}")
    private String fileUploadDir;
    @Value("${app.upload.file.url-path}")
    private String fileUploadUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(imgUploadUrl + "**")
                .addResourceLocations("file:/" + imgUploadDir);

        registry.addResourceHandler(fileUploadUrl + "**")
                .addResourceLocations("file:/" + fileUploadDir);
        super.addResourceHandlers(registry);
    }
}
