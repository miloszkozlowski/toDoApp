package pl.mihome.toDoApp.controller;

import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	Set<HandlerInterceptor> wszystkieInterceptory;
	
	
	
	public MvcConfig(Set<HandlerInterceptor> wszystkieInterceptory) {
		super();
		this.wszystkieInterceptory = wszystkieInterceptory;
	}



	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		WebMvcConfigurer.super.addInterceptors(registry);
		//registry.addInterceptor(new LoggerInterceptor());
		
		wszystkieInterceptory.forEach(registry::addInterceptor);
	}

}
