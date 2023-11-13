package com.mylogistics.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylogistics.exception.InvalidIdException;
import com.mylogistics.model.Order;
import com.mylogistics.model.Route;
import com.mylogistics.repository.OrderRepository;

@Service
public class OrderService {
@Autowired
private OrderRepository orderRepository;
	public Order postOrder(Order order) {
		// TODO Auto-generated method stub
		return orderRepository.save(order);
	}
	public Order getOrderById(int oid) throws InvalidIdException{
		// TODO Auto-generated method stub
		Optional<Order> optional = orderRepository.findById(oid);
		 if(!optional.isPresent())
				throw new InvalidIdException("Order Id Invalid");
			return optional.get();
	}

}
