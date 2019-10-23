package com.example.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.community.dto.paginationDTO;
import com.example.community.service.QuestionService;

@Controller
public class IndexContorller {
	
	@Autowired
	private QuestionService questionService;

	@GetMapping("/")
	private String index(
			Model model,
			@RequestParam(name="page",defaultValue="1") Integer page,
			@RequestParam(name="size",defaultValue="2") Integer size
			) {
		System.out.println("hello");
		paginationDTO pagination = questionService.list(page,size);
		model.addAttribute("pagination", pagination);
		return "index";
	}

}
