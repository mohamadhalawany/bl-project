package com.bl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class WebConfig extends WebMvcConfigurerAdapter {//WebSecurityConfigurerAdapter{//WebMvcConfigurerAdapter {

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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

		String uploadFolder = env.getProperty("uploads") ; //"/UPLOADS/**" ;
		String uploadPath = env.getProperty("upload_path") ; //"file:///D:/UPLOADS/" ;
		
		registry.addResourceHandler(uploadFolder).addResourceLocations(uploadFolder , uploadPath);
    }
}
