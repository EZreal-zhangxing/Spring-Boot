package com.zx.Filter;

import javax.servlet.Filter;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class FilterConfigureClass {
	@Bean
	public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(SelfFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("selfFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
	@Bean(name = "selfFilter")
    public Filter SelfFilter() {
        return new SelfFilter();
    }
}
