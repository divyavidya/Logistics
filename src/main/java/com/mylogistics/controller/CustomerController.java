package com.mylogistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mylogistics.enums.RoleType;
import com.mylogistics.model.Customer;
import com.mylogistics.model.User;
import com.mylogistics.service.CustomerService;
import com.mylogistics.service.UserService;



@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@PostMapping("/customer/signup")
	public Customer postCustomer(@RequestBody Customer customer) {
		User user = customer.getUser();
		/*I am encrypting the password*/
		String passwordPlain=user.getPassword();
		String encodedPassword=passwordEncoder.encode(passwordPlain);
		user.setPassword(encodedPassword);
		user.setRole(RoleType.CUSTOMER);
		//Step 1 Save user in db and attach saved user to customer
		user = userService.postUser(user);
		//Step 2: Attach user and save customer
		customer.setUser(user);
		customer = customerService.postCustomer(customer);
		return customer; 
	}
}
