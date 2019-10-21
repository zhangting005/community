package com.example.community.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.community.dto.paginationDTO;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.service.QuestionService;

@Controller
public class ProfileController {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/profile/{action}")
	public String Profile(@PathVariable(name = "action") String action,
			Model model,
			HttpServletRequest request,
			@RequestParam(name="page",defaultValue="1") Integer page,
			@RequestParam(name="size",defaultValue="2") Integer size) {
		Cookie[] cookies = request.getCookies();
		User user = null;
		if (cookies != null && cookies.length != 0) {
			for (Cookie cookie : cookies) {
				System.out.println("cookies" + cookie.getName());
				if (cookie.getName().equals("token")) {
					System.out.println("cookie.getName():" + cookie.getName());
					String token = cookie.getValue();
					System.out.println("token:" + token);
					user = userMapper.findByToken(token);
					if (user != null) {
						System.out.println("user!=null");
						request.getSession().setAttribute("user", user);
					}
					break;
				}
			}
		}
		
		if(user == null) {
			return "redirect:/";
		}
		
		if("questions".equals(action)) {
			model.addAttribute("section","questions");
			model.addAttribute("sectionName","我的提问");
		}else if("replies".equals(action)){
			model.addAttribute("section","replies");
			model.addAttribute("sectionName","最新回复");
		}
		paginationDTO paginationDTO = questionService.listByUserId(user.getId(), page,size);
		model.addAttribute("pagination", paginationDTO);
		
		return "profile";
	}

}
