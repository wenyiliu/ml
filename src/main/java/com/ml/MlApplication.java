package com.ml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/**
 * @author liuwenyi
 * @date 2019/6/5
 */
@SpringBootApplication
public class MlApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MlApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MlApplication.class);
	}
}
