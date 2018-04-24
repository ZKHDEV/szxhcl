package cn.edu.gdou.szxhcl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:jdbc.properties")
//@PropertySource("classpath:thymeleaf.properties")
public class SzxhclApplication {

	public static void main(String[] args) {
		SpringApplication.run(SzxhclApplication.class, args);
	}
}
