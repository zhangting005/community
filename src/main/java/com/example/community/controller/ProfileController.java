package com.example.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.community.dto.paginationDTO;
import com.example.community.model.User;
import com.example.community.service.QuestionService;

@Controller
public class ProfileController {
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/profile/{action}")
	public String Profile(@PathVariable(name = "action") String action,
			Model model,
			HttpServletRequest request,
			@RequestParam(name="page",defaultValue="1") Integer page,
			@RequestParam(name="size",defaultValue="2") Integer size) {
		
		User user = (User) request.getSession().getAttribute("user");
		
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
