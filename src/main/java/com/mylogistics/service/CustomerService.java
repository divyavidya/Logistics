package com.mylogistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mylogistics.model.Customer;
import com.mylogistics.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepository;

	public Customer postCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return customerRepository.save(customer);
	}
	public List<Customer> getAllCustomers(Pageable pageable) {
		return customerRepository.findAll(pageable).getContent();
	}
}
