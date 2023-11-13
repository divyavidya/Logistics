package com.mylogistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mylogistics.enums.RoleType;
import com.mylogistics.model.Customer;
import com.mylogistics.model.Executive;
import com.mylogistics.model.User;
import com.mylogistics.service.ExecutiveService;
import com.mylogistics.service.UserService;

@RestController
public class ExecutiveController {
	@Autowired
	private ExecutiveService executiveService;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@PostMapping("/executive/add")
	public Executive postExecutive(@RequestBody Executive executive) {
		User user = executive.getUser();
		/*I am encrypting the password*/
		String passwordPlain=user.getPassword();
		String encodedPassword=passwordEncoder.encode(passwordPlain);
		user.setPassword(encodedPassword);
		user.setRole(RoleType.EXECUTIVE);
		//Step 1 Save user in db and attach saved user to customer
		user = userService.postUser(user);
		//Step 2: Attach user and save customer
		executive.setUser(user);
		executive = executiveService.postExecutive(executive);
		return executive; 
	}
}
