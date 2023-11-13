package com.mylogistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mylogistics.enums.RoleType;
import com.mylogistics.model.Carrier;
import com.mylogistics.model.User;
import com.mylogistics.service.CarrierService;
import com.mylogistics.service.UserService;

@RestController
public class CarrierController {

	@Autowired
	private CarrierService carrierService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/carrier/add")
	public Carrier postCustomer(@RequestBody Carrier carrier) {
		User user = carrier.getUser();
		user.setRole(RoleType.CARRIER);
		//Step 1 Save user in db and attach saved user to customer
		user = userService.postUser(user);
		//Step 2: Attach user and save carrier
		carrier.setUser(user);
		carrier = carrierService.postCustomer(carrier);
		return carrier; 
	}
	
}
