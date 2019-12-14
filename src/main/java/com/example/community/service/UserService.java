package com.example.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;

	public void createOrUpdate(User user) {
		User dbUser = userMapper.findAccountId(user.getAccountId());
		if(dbUser == null) {
			//插入
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(user);
		}else {
			dbUser.setGmtModified(System.currentTimeMillis());
			dbUser.setAvatarUrl(user.getAvatarUrl());
			dbUser.setName(user.getName());
			dbUser.setToken(user.getToken());
			userMapper.update(dbUser);
			//更新
		}
	}
	

}
