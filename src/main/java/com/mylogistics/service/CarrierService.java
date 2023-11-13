package com.mylogistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mylogistics.model.Carrier;
import com.mylogistics.repository.CarrierRepository;

@Service
public class CarrierService {
	@Autowired
	private CarrierRepository carrierRepository;

	public Carrier postCustomer(Carrier carrier) {
		// TODO Auto-generated method stub
		return carrierRepository.save(carrier);
	}

	public List<Carrier> getAllCarriers(Pageable pageable) {
		// TODO Auto-generated method stub
		return carrierRepository.findAll(pageable).getContent();
	}

}
