package com.example.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexContorller {
	
	@GetMapping("/")
	private String hello() {
		return "index";
	}

}
 