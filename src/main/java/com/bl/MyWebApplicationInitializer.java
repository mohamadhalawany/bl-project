package com.bl;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
//	return new Class<?>[] { SecurityInit.class, WebConfig.class };
	return new Class<?>[]{SecurityInit.class , WebConfig.class};
	// return null ;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
	return new Class<?>[] { SpringMvc1Application.class };
    }

    @Override
    protected String[] getServletMappings() {
	return new String[] { "/" };
    }

}
