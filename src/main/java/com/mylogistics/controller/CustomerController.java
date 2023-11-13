package com.mylogistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mylogistics.enums.RoleType;
import com.mylogistics.exception.InvalidIdException;
import com.mylogistics.model.Customer;
import com.mylogistics.model.Order;
import com.mylogistics.model.Product;
import com.mylogistics.model.Receiver;
import com.mylogistics.model.Route;
import com.mylogistics.model.User;
import com.mylogistics.service.CustomerService;
import com.mylogistics.service.OrderService;
import com.mylogistics.service.ProductService;
import com.mylogistics.service.ReceiverService;
import com.mylogistics.service.RouteService;
import com.mylogistics.service.UserService;



@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RouteService routeService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ReceiverService receiverService;
	@Autowired
	private OrderService orderService;
	
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
	
	
	@PostMapping("/customer/{cid}/{source}/{destination}")
	public ResponseEntity<?> placeOrder(@RequestBody Order order,@PathVariable("cid") int cid,@PathVariable("source") String source, 
			@PathVariable("destination") String destination) {
		try{
			Route route = routeService.getBySrcDest(source,destination);
			order.setRoute(route);
			double distance=route.getDistance();
			int days=Integer.parseInt(route.getNoOfDays());
			double cost = distance*days;
			order.setCost(cost);
			order.setStatus("Ready to ship");
			Customer customer=customerService.getOne(cid);
			order.setCustomer(customer);
			Product product = productService.postProduct(order.getProduct());
			order.setProduct(product);
			Receiver receiver=receiverService.postReceiver(order.getReceiver());
			order.setReceiver(receiver);
			order=orderService.postOrder(order);
			return ResponseEntity.ok().body(order);
			
		}catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
