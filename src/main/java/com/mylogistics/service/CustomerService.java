package com.mylogistics.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mylogistics.exception.InvalidIdException;
import com.mylogistics.model.Customer;
import com.mylogistics.model.Route;
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
	public Customer getOne(int cid) throws InvalidIdException{
		// TODO Auto-generated method stub
		Optional<Customer> optional = customerRepository.findById(cid);
		 if(!optional.isPresent())
				throw new InvalidIdException("customer id is invalid");
			return optional.get();
	}
}
