package com.mylogistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mylogistics.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
