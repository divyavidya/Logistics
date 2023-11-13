package com.mylogistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mylogistics.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
