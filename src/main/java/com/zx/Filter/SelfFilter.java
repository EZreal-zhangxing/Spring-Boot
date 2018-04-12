package com.zx.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;


public class SelfFilter implements Filter{
	Logger log=Logger.getLogger(SelfFilter.class);
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		log.info(this.getClass().getName() + " 初始化");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info(request.getLocalName() + " 发来的请求");
		chain.doFilter(request, response);
	}

	public void destroy() {
		// TODO Auto-generated method stub
		log.info(this.getClass().getName() + " 销毁");
	}
	
}
