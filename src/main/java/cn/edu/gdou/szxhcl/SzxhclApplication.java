package cn.edu.gdou.szxhcl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@PropertySource("classpath:jdbc.properties")
public class SzxhclApplication {

	public static void main(String[] args) {
		SpringApplication.run(SzxhclApplication.class, args);
	}
}
