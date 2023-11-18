package com.mylogistics.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mylogistics.exception.InvalidIdException;
import com.mylogistics.model.Order;
import com.mylogistics.repository.OrderRepository;

@Service
public class OrderService {
@Autowired
private OrderRepository orderRepository;
	public Order postOrder(Order order) {
		return orderRepository.save(order);
	}
	
	public Order getOrderById(int oid) throws InvalidIdException{
		Optional<Order> optional = orderRepository.findById(oid);
		 if(!optional.isPresent())
				throw new InvalidIdException("Order Id Invalid");
			return optional.get();
	}
	
	public List<Order> getOrdersByCarrier(int caid, Pageable pageable) {
		return orderRepository.findByCarrierId(caid,pageable);
	}

	public List<Order> getOrdersByCustomer(int cid, Pageable pageable) {
		return orderRepository.findByCustomerId(cid,pageable);
	}

	public List<Order> getAllOrders(Pageable pageable) {
		return orderRepository.findAll(pageable).getContent();
	}


}
