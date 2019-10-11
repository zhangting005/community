package com.example.community.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;

@Controller
public class IndexContorller {
	
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping("/")
	private String hello(HttpServletRequest request) {
		System.out.println("hello");
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println("cookies"+cookie.getName());
			if(cookie.getName().equals("token")) {
				System.out.println("cookie.getName():"+cookie.getName());
				String token = cookie.getValue();
				System.out.println("token:"+token);
				User user  = userMapper.findByToken(token);
				if(user!=null) {
					System.out.println("user!=null");
					request.getSession().setAttribute("user", user);
				}
				break;
			}
		}
		
		return "index";
	} 

}
 