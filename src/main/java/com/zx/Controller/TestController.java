package com.zx.Controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zx.Pojo.User;
import com.zx.Redis.redisClass;
import com.zx.Service.UserService;

@RestController
public class TestController {
	@Autowired
	private redisClass redisclass;
	
	@Autowired
	public UserService userService;
	
	@Value("${propertiesStr}")
	private String proStr;//从properties文件获取值
	
	@RequestMapping("/hello")
	public String HelloMethod(){
		return "hello world "+ proStr;
	}
	@RequestMapping("/getuser")
	public List<User> getuser(){
		return userService.getUser();
	}
	@PostConstruct
	public void init_method(){
		System.out.println(this.getClass().getName() + " init redisclass is :"+redisclass);
	}
	@RequestMapping("/getchannel/{channel}")
	public String getchannel(@PathVariable String channel){
		redisclass.subscribe(channel);
		return "订阅成功！";
	}
	
	@RequestMapping("/sendmessage/{channel}/{message}")
	public String sendmessage(@PathVariable String channel,@PathVariable String message){
		Long result=redisclass.publish(channel, message);
		return "返回结果 ："+result;
	}
	
}
