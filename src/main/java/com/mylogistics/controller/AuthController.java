package com.mylogistics.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylogistics.model.User;
import com.mylogistics.service.UserService;


@RestController
public class AuthController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/user/login")
	public User login(Principal principal) {
		//ask spring, who has logged in?? i will give the username
		String username = principal.getName();
		User user = (User)userService.loadUserByUsername(username);
		return user;
	}
}
