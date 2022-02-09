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
    
    private final String URL = "url";
    private final String USER = "user";
    private final String PASSWORD = "password";
    private final String DRIVER = "driverClassName";
    
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



    
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	auth.jdbcAuthentication().dataSource(ds()).passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//	http.authorizeRequests()
//	.antMatchers("/login")
//	.permitAll()
//	.anyRequest()
//	.authenticated()
//	.and()
//	.formLogin()
//	.loginPage("/login")
//	.loginProcessingUrl("/doLogin")
//	.defaultSuccessUrl("/")
//	.and()
//	.logout()
//	.logoutUrl("/logout")
//	.permitAll()
//	.logoutSuccessUrl("/login");
//    }

//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
//	return factory -> factory.setContextPath("/stock");
//    }
}
