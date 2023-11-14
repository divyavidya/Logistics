package com.mylogistics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mylogistics.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query("select o from Order o where o.carrier.id=?1")
	List<Order> findByCarrierId(int caid);

}
