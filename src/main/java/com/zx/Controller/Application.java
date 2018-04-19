package com.zx.Controller;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import com.sun.corba.se.pept.protocol.ProtocolHandler;


@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
@ComponentScan(basePackages={"com.zx"})//Spring boot 默认扫描Application入口类所在的包 兄弟包父包并不会扫描 需要手动配置扫描包
public class Application extends TomcatEmbeddedServletContainerFactory{
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Override
	public EmbeddedServletContainer getEmbeddedServletContainer(
			ServletContextInitializer... arg0) {
//		this.setPort(9000);
		return super.getEmbeddedServletContainer(arg0);
	}
	@Override
	protected void customizeConnector(Connector connector) {
		// TODO Auto-generated method stub
		super.customizeConnector(connector);
		Http11NioProtocol ph=(Http11NioProtocol) connector.getProtocolHandler();
		ph.setMaxConnections(200);
		ph.setMaxThreads(50);
		ph.setConnectionTimeout(30000);
	}
}
