package com.example.community.controller;

import java.util.List;



import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.community.dto.QuestionDTO;
import com.example.community.dto.paginationDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import com.example.community.service.QuestionService;
import com.example.community.service.UserService;

@Controller
public class IndexContorller {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private QuestionService questionService;

	@GetMapping("/")
	private String index(HttpServletRequest request,
			Model model,
			@RequestParam(name="page",defaultValue="1") Integer page,
			@RequestParam(name="size",defaultValue="2") Integer size
			) {
		System.out.println("hello");
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length != 0) {
			for (Cookie cookie : cookies) {
				System.out.println("cookies" + cookie.getName());
				if (cookie.getName().equals("token")) {
					System.out.println("cookie.getName():" + cookie.getName());
					String token = cookie.getValue();
					System.out.println("token:" + token);
					User user = userMapper.findByToken(token);
					if (user != null) {
						System.out.println("user!=null");
						request.getSession().setAttribute("user", user);
					}
					break;
				}
			}
		}
		paginationDTO pagination = questionService.list(page,size);
		model.addAttribute("pagination", pagination);
		return "index";
	}

}
