package com.jwt.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class MyController {

	@GetMapping("/welcome")
	public String welcome() {
		System.out.println("Inside the welcome method !!");
		return "Welcome !!";
	}
	
}
