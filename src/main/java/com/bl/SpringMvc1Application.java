package com.bl;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.collect.ImmutableMap;


@SpringBootApplication
public class SpringMvc1Application {

    @Autowired
    private Environment env;

    private final String URL = "spring.datasource.url" ; //"url";
    private final String USER = "spring.datasource.username" ; //"user";
    private final String PASSWORD = "spring.datasource.password" ; //"password";
    private final String DRIVER = "spring.datasource.driver-class-name" ;//"driverClassName";

    @Bean
    public DataSource ds() {
	
		DriverManagerDataSource dmds = new DriverManagerDataSource();
		dmds.setUrl(env.getProperty(URL));
		dmds.setUsername(env.getProperty(USER));
		dmds.setPassword(env.getProperty(PASSWORD));
		dmds.setDriverClassName(env.getProperty(DRIVER));
	
		return dmds;
    }

    @Bean
    public ViewResolver viewResolver() {
		InternalResourceViewResolver view = new InternalResourceViewResolver();
		view.setSuffix(".xhtml");
		return view;
    }

    @Bean
    public static CustomScopeConfigurer viewScope() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		configurer.setScopes(new ImmutableMap.Builder<String, Object>().put("view", new ViewScope()).build());
		return configurer;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipart = new CommonsMultipartResolver();		
		return multipart;
    }

    @Bean
    @Order(0)
    public MultipartFilter multipartFilter() {
		MultipartFilter multipartFilter = new MultipartFilter();
		multipartFilter.setMultipartResolverBeanName("multipartReso‌​lver");
		return multipartFilter;
    }

    
}
