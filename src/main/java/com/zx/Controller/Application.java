package com.zx.Controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
@ComponentScan(basePackages={"com.zx"})//Spring boot 默认扫描Application入口类所在的包 兄弟包父包并不会扫描 需要手动配置扫描包
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
