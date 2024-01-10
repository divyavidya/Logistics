package com.mylogistics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mylogistics.enums.CarrierStatus;
import com.mylogistics.exception.InvalidIdException;
import com.mylogistics.model.Carrier;
import com.mylogistics.model.Order;
import com.mylogistics.service.CarrierService;
import com.mylogistics.service.OrderService;
import com.mylogistics.service.RouteService;


@RestController
@RequestMapping("/carrier")
@CrossOrigin(origins = {"http://localhost:3000"})
public class CarrierController {

	@Autowired
	private CarrierService carrierService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private RouteService routeService;
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
	public ResponseEntity<?> getOrdersByCarrier(@PathVariable("caid") int caid,@RequestParam(value="page",required=false,defaultValue="0") Integer page,
			@RequestParam(value="size",required=false,defaultValue="100000")Integer size){
		try {
			//get order by CarrierId
			Carrier carrier =carrierService.getCarrierById(caid);
			Pageable pageable =PageRequest.of(page, size);
			List<Order> list = orderService.getOrdersByCarrier(caid,pageable);
			return ResponseEntity.ok().body(list);
		}catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/getLocations/{source}")
	public List<String> getLocationsBySource(@PathVariable("source") String source){
		List<String> list=routeService.getLocationBySource(source);
		return list;
	}
	
	@PutMapping("/updateCarrier/{cid}")
	public ResponseEntity<?> putCarrier(@PathVariable("cid") int cid,@RequestBody Carrier newCarrier) {
		try { 
			Carrier carrier = carrierService.getCarrierById(cid);
			if (newCarrier.getName() != null)
				carrier.setName(newCarrier.getName());
			if (newCarrier.getCity() != null)
				carrier.setCity(newCarrier.getCity());
			if (newCarrier.getAddress() != null)
				carrier.setAddress(newCarrier.getAddress());
			if (newCarrier.getStatus()!= null)
				carrier.setStatus(newCarrier.getStatus());
			if (newCarrier.getContact() != null)
				carrier.setContact(newCarrier.getContact());
			if (newCarrier.getEmail() != null)
				carrier.setEmail(newCarrier.getEmail());
			carrier=carrierService.postCarrier(carrier);
			return ResponseEntity.ok().body(carrier);
		}
		catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("/details/{caid}")
	public ResponseEntity<?> getDetails(@PathVariable("caid") int caid){
		try {
			Carrier carrier=carrierService.getCarrierById(caid);
			return ResponseEntity.ok().body(carrier);
		}
		catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage()); 
		}
	}
}
