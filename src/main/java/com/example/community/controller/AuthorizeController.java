package com.example.community.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.GithubUser;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.provider.GithubProvider;

@Controller
public class AuthorizeController {
	
	@Autowired
	private GithubProvider githubProvider;
	
	@Value("${github.client.id}")
	private String clientId;
	
	@Value("${github.client.secret}")
	private String clientSecret;
	
	@Value("${github.redirect.uri}")
	private String redirectUri;
	
	@Autowired
	private  UserMapper userMapper;
	
	@GetMapping("callback")
	public String callback(@RequestParam(name = "code")String code,@RequestParam(name = "state")String state,HttpServletRequest request
			,HttpServletResponse response) {
		//session是通过HttpServletRequest拿到的
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(redirectUri);
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setClient_secret(clientSecret);
		accessTokenDTO.setState(state);
		String accessToken = githubProvider.getAccessToken(accessTokenDTO);
		GithubUser githubUser = githubProvider.getUser(accessToken);
		System.out.println(githubUser.getId());
		if(githubUser !=null && githubUser.getId()!=null) {
			User user = new User();
			String token = UUID.randomUUID().toString();
			user.setToken(token);
			user.setName(githubUser.getName());
			user.setAccountId(String.valueOf(githubUser.getId()));
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			user.setAvatarUrl(githubUser.getAvatarUrl());
			userMapper.insert(user);
			//手动写入cookie,cookie是在response里面
			//访问首页时，需要将cookie里key为token的信息拿到，到数据库里查，用来验证是否登录成功
			response.addCookie(new Cookie("token",token));
			request.getSession().setAttribute("user", githubUser);
			return "redirect:/";//用redirct地址重新重定向到这个页面
			//登录成功，写cookie和session
		}else {
			//登录失败，重新登录
			return "redirect:/";
		}
	}

}
