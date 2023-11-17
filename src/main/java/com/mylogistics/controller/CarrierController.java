package com.mylogistics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylogistics.exception.InvalidIdException;
import com.mylogistics.model.Carrier;
import com.mylogistics.model.Order;
import com.mylogistics.service.CarrierService;
import com.mylogistics.service.OrderService;


@RestController
@RequestMapping("/carrier")
public class CarrierController {

	@Autowired
	private CarrierService carrierService;
	@Autowired
	private OrderService orderService;
	
	
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
	public ResponseEntity<?> getOrdersByCarrier(@PathVariable("caid") int caid){
		try {
			//get order by CarrierId
			Carrier carrier =carrierService.getCarrierById(caid);
			List<Order> list = orderService.getOrdersByCarrier(caid);
			return ResponseEntity.ok().body(list);
		}catch(InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
