package com.mylogistics.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mylogistics.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	

	List<Order> findByCustomerId(int cid,Pageable pageable);

	List<Order> findByCarrierId(int caid, Pageable pageable);

}
