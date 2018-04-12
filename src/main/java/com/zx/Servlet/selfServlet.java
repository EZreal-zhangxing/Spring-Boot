package com.zx.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zx.Filter.SelfFilter;

public class selfServlet extends HttpServlet {
	Logger log=Logger.getLogger(HttpServlet.class);
	private static final long serialVersionUID = 1L;
	@Override
	public void init() throws ServletException {
		log.info(this.getClass().getName() + " 初始化");
		super.init();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.info(this.getClass().getName() +" doGet");
		super.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.info(this.getClass().getName() +" doPost");
		super.doPost(req, resp);
	}
}
