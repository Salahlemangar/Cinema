package org.sid.cinema.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;


@Configuration 
public class ThymeleafMvcWebConfig  implements WebMvcConfigurer{
	
	@Autowired
	  ApplicationContext applicationContext;

	@Bean
	 public ViewResolver viewResolver() {
		    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		    resolver.setTemplateEngine(templateEngine());
		    resolver.setCharacterEncoding("UTF-8");
		    return resolver;
		  }
	@Bean
	 public ITemplateEngine templateEngine() {
		    SpringTemplateEngine engine = new SpringTemplateEngine();
		    
		    engine.setTemplateResolver(templateResolver());
		    engine.addDialect(new LayoutDialect());	
		    return engine;
		  }

		  private ITemplateResolver templateResolver() {
		    SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		    resolver.setApplicationContext(this.applicationContext);
		    resolver.setCharacterEncoding("UTF-8");
		    resolver.setTemplateMode(TemplateMode.HTML);
		    resolver.setCacheable(false);
		    resolver.setPrefix("classpath:/templates/");
		    resolver.setSuffix(".html");
		    return resolver;
		  }
	
}
