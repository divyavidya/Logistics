package com.mylogistics.controller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mylogistics.dto.OrderDto;
import com.mylogistics.enums.RoleType;
import com.mylogistics.enums.StatusType;
import com.mylogistics.exception.InvalidIdException;
import com.mylogistics.model.Carrier;
import com.mylogistics.model.Customer;
import com.mylogistics.model.Order;
import com.mylogistics.model.Product;
import com.mylogistics.model.Receiver;
import com.mylogistics.model.Route;
import com.mylogistics.model.User;
import com.mylogistics.service.CarrierService;
import com.mylogistics.service.CustomerService;
import com.mylogistics.service.OrderService;
import com.mylogistics.service.ProductService;
import com.mylogistics.service.ReceiverService;
import com.mylogistics.service.RouteService;
import com.mylogistics.service.UserService;



@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
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
	@Autowired
	private CarrierService carrierService;
	
	@Autowired
	private Logger logger;
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
	
	
	@PostMapping("/customer/{cid}")
	public ResponseEntity<?> placeOrder(@RequestBody OrderDto dto, @PathVariable("cid") int cid) {
		try{
			//get route by source,destination and save in order
			String source = dto.getSource();
			String destination = dto.getDestination();
			Route route = routeService.getBySrcDest(source,destination);
			Order order = new Order();
			order.setRoute(route);
			//calculate cost
			double distance=route.getDistance();
			int days=route.getNoOfDays();
			double cost = distance*days;
			order.setCost(cost);
			order.setStatus(StatusType.PENDING);
			//fetch customer id and save in order
			Customer customer=customerService.getOne(cid);
			order.setCustomer(customer);
			//save product details in db and save product id in order
			Product product = productService.postProduct(dto.getProduct());
			order.setProduct(product);
			//save receiver details in db and save receiver id in order
			Receiver receiver=receiverService.postReceiver(dto.getReceiver());
			order.setReceiver(receiver);
			order.setPickUpAddress(dto.getPickUpAddress());
			order.setPickUpDate(dto.getPickUpDate());
			List<Carrier> list=carrierService.getCarrier(source);
			Carrier carrier=list.get(0);
			order.setCarrier(carrier);
			order=orderService.postOrder(order);
			logger.info("Order placed successfully");
			return ResponseEntity.ok().body(order);
			 
		}catch(InvalidIdException e) {
			logger.error("Issue in placing order");
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/customer/getAllOrdersHistory/{cid}")
	public ResponseEntity<?> getOrdersByCustomer(@PathVariable("cid") int cid,@RequestParam(value="page",required=false,defaultValue="0") Integer page,
			@RequestParam(value="size",required=false,defaultValue="100000")Integer size){
		try {
			//get order by CarrierId
			Customer customer =customerService.getCustomerById(cid);
			Pageable pageable =PageRequest.of(page, size);
			List<Order> list = orderService.getOrdersByCustomer(cid,pageable);
			return ResponseEntity.ok().body(list);
		}catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("customer/getOrder/{oid}")
	public ResponseEntity<?> getOneOrder(@PathVariable("oid") int oid) {
		try {
			Order order=orderService.getOrderById(oid);
			return ResponseEntity.ok().body(order);
		}
		catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	@GetMapping("/getLocations/{source}")
	public List<String> getLocationBySource(@PathVariable("source") String source){
		List<String> list=routeService.getLocationBySource(source);
		return list;
	}
}
