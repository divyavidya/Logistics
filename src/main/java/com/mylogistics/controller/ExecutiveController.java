package com.mylogistics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mylogistics.enums.CarrierStatus;
import com.mylogistics.enums.RoleType;
import com.mylogistics.enums.StatusType;
import com.mylogistics.exception.InvalidIdException;
import com.mylogistics.model.Carrier;
import com.mylogistics.model.Customer;
import com.mylogistics.model.Executive;
import com.mylogistics.model.Order;
import com.mylogistics.model.Route;
import com.mylogistics.model.User;
import com.mylogistics.service.CarrierService;
import com.mylogistics.service.CustomerService;
import com.mylogistics.service.ExecutiveService;
import com.mylogistics.service.OrderService;
import com.mylogistics.service.RouteService;
import com.mylogistics.service.UserService;


@RestController
@RequestMapping("/executive")
@CrossOrigin(origins = {"http://localhost:3000"})
public class ExecutiveController {
	@Autowired
	private ExecutiveService executiveService;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RouteService routeService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CarrierService carrierService;
	@Autowired
	private OrderService orderService;
	@PostMapping("/add")
	public Executive postExecutive(@RequestBody Executive executive) {
		User user = executive.getUser();
		/* I am encrypting the password */
		String passwordPlain = user.getPassword();
		String encodedPassword = passwordEncoder.encode(passwordPlain);
		user.setPassword(encodedPassword);
		user.setRole(RoleType.EXECUTIVE);
		// Step 1 Save user in db and attach saved user to customer
		user = userService.postUser(user);
		// Step 2: Attach user and save customer
		executive.setUser(user);
		executive = executiveService.postExecutive(executive);
		return executive;
	}
	@PostMapping("/carrierOnboard")
	public Carrier postCarrier(@RequestBody Carrier carrier) {
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
		carrier.setStatus(CarrierStatus.AVAILABLE);
		carrier = carrierService.postCarrier(carrier);
		return carrier; 
	}

	@PostMapping("/addRoute")
	public Route postExecutive(@RequestBody Route route) {
		route = routeService.postRoute(route);
		return route;
	}

	@GetMapping("/getallRoutes")
	public List<Route> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "100000") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return routeService.getAll(pageable);
	}

	@GetMapping("/getallCustomers")
	public List<Customer> getAllCustomers(
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "100000") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return customerService.getAllCustomers(pageable);
	} 

	@GetMapping("/getallCarriers")
	public List<Carrier> getAllCarriers(
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "100000") Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		return carrierService.getAllCarriers(pageable);
	}
	
	@GetMapping("/allOrders")
	public List<Order> getAllOrders(
			@RequestParam(value="page",required=false,defaultValue="0") Integer page,
			@RequestParam(value="size",required=false,defaultValue="100000")Integer size){
		Pageable pageable =PageRequest.of(page, size);
		return orderService.getAllOrders(pageable);
	}

	@PutMapping("/putCarrier/{oid}/{caid}")
	public ResponseEntity<?> updateCarrier(@PathVariable("oid") int oid, @PathVariable("caid") int caid,
			@RequestBody Order newOrder) {
		try { 
			Carrier carrier = carrierService.getCarrierById(caid);
			Order order = orderService.getOrderById(oid);
			order.setCarrier(carrier);
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

			order = orderService.postOrder(order);
			return ResponseEntity.ok().body(order);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@PutMapping("/putRoute/{rid}")
	public ResponseEntity<?> updateRoute(@PathVariable("rid") int rid,
			@RequestBody Route newRoute) {
		try { 
			Route route = routeService.getRouteById(rid);
			if (newRoute.getSource()!=null)
				route.setSource(newRoute.getSource());
			if(newRoute.getDestination()!=null)
				route.setDestination(newRoute.getDestination());
			if(newRoute.getDistance()!=0)
				route.setDistance(newRoute.getDistance());
			if(newRoute.getVehicle()!=null)
				route.setVehicle(newRoute.getVehicle());
			if(newRoute.getNoOfDays()!=0)
				route.setNoOfDays(newRoute.getNoOfDays());

			route = routeService.postRoute(route);
			return ResponseEntity.ok().body(route);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@DeleteMapping("/route/delete/{rid}")
	public ResponseEntity<?> deleteRoute(@PathVariable("rid") int rid){
		try {
			Route route=routeService.getRouteById(rid);
			routeService.deleteRoute(route.getId());
			return ResponseEntity.ok().body("Route Record Deleted");
		}catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@DeleteMapping("/carrier/delete/{caid}")
	public ResponseEntity<?> deleteCarrier(@PathVariable("caid") int caid){
		try {
			Carrier carrier=carrierService.getCarrierById(caid);
			carrierService.deleteCarrier(carrier.getId());
			userService.deleteUser(carrier.getUser());
			return ResponseEntity.ok().body("Carrier Record Deleted");
		}catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/getCarriersbySource/{source}")
	public List<Carrier> getAllCarriersBySource(@PathVariable("source") String source) {
	List<Carrier> list=carrierService.getCarrier(source);
	return list;
	}
	@GetMapping("/getAllCities")
	public List<String> getAllCities(){
		List<String> list=routeService.getAllCities();
		return list;
	}
	@GetMapping("/desiLocations")
	public List<String> designatedLocations(){
		List<String> list=routeService.designatedLocations();
		return list;
	}
}
