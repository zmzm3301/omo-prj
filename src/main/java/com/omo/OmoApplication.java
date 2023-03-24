package com.omo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableJpaAuditing
@SpringBootApplication
public class OmoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmoApplication.class, args);
	}
	
	@Configuration
	public class ReactRoutingConfiguration implements WebMvcConfigurer {

	    @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        registry.addViewController("/{spring:(?!api$).*$}/**").setViewName("forward:/index.html");
	    }
	}

}
