package com.mylogistics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mylogistics.enums.RoleType;
import com.mylogistics.exception.InvalidIdException;
import com.mylogistics.model.Carrier;
import com.mylogistics.model.Order;
import com.mylogistics.model.User;
import com.mylogistics.service.CarrierService;
import com.mylogistics.service.OrderService;
import com.mylogistics.service.UserService;


@RestController
@RequestMapping("/carrier")
public class CarrierController {

	@Autowired
	private CarrierService carrierService;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private OrderService orderService;
	@PostMapping("/add")
	public Carrier postCustomer(@RequestBody Carrier carrier) {
		User user = carrier.getUser();
		/*I am encrypting the password*/
		String passwordPlain=user.getPassword();
		String encodedPassword=passwordEncoder.encode(passwordPlain);
		user.setPassword(encodedPassword);
		user.setRole(RoleType.CARRIER);
		//Step 1 Save user in db and attach saved user to customer
		user = userService.postUser(user);
		//Step 2: Attach user and save carrier
		carrier.setUser(user);
		carrier = carrierService.postCustomer(carrier);
		return carrier; 
	}
	
	@PutMapping("/putCarrier/{oid}")
	public ResponseEntity<?> updateCarrier(@PathVariable("oid") int oid,
			@RequestBody Order newOrder) {
		try {
			Order order = orderService.getOrderById(oid);
			if (newOrder.getPickUpAddress() != null)
				order.setPickUpAddress(newOrder.getPickUpAddress());
			if (newOrder.getPickUpDate() != null)
				order.setPickUpDate(newOrder.getPickUpDate());
			if (newOrder.getCost() != 0)
				order.setCost(newOrder.getCost());
			if (newOrder.getStatus() != null)
				order.setStatus(newOrder.getStatus());
			if (newOrder.getCustomer() != null)
				order.setCustomer(newOrder.getCustomer());
			if (newOrder.getProduct() != null)
				order.setProduct(newOrder.getProduct());
			if (newOrder.getReceiver() != null)
				order.setReceiver(newOrder.getReceiver());
			if (newOrder.getRoute() != null)
				order.setRoute(newOrder.getRoute());
			if(newOrder.getCarrier() != null)
				order.setCarrier(newOrder.getCarrier());
			order = orderService.postOrder(order);
			return ResponseEntity.ok().body(order);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/getAllOrders/{caid}")
	public ResponseEntity<?> getProductsByVendor(@PathVariable("caid") int caid){
		try {
			Carrier carrier =carrierService.getCarrierById(caid);
			List<Order> list = orderService.getOrdersByCarrier(caid);
			return ResponseEntity.ok().body(list);
		}catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
