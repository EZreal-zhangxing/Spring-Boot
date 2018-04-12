package com.zx.Servlet;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfigureClass {
	@Bean
	public ServletRegistrationBean servletRegistBean(){
		return new ServletRegistrationBean(new selfServlet(), "/selfServlet");
	}
}
