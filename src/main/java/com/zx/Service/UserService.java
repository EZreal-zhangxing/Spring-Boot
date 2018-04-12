package com.zx.Service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zx.Dao.UserMapper;
import com.zx.Pojo.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	
	public List<User> getUser(){
		return userMapper.getUserInfo();
	}
	@PostConstruct
	public void init_method(){
		System.out.println(this.getClass().getName() + " init");
	}
}
