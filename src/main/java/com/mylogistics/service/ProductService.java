package com.mylogistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylogistics.model.Product;
import com.mylogistics.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	public Product postProduct(Product product) {
		return productRepository.save(product);
	}

}
