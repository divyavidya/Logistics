package com.mylogistics.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mylogistics.exception.InvalidIdException;
import com.mylogistics.model.Carrier;
import com.mylogistics.repository.CarrierRepository;

@Service
public class CarrierService {
	@Autowired
	private CarrierRepository carrierRepository;

	public Carrier postCustomer(Carrier carrier) {
		return carrierRepository.save(carrier);
	}

	public List<Carrier> getAllCarriers(Pageable pageable) {
		return carrierRepository.findAll(pageable).getContent();
	}

	public Carrier getCarrierById(int caid) throws InvalidIdException{
		Optional<Carrier> optional = carrierRepository.findById(caid);
		 if(!optional.isPresent())
				throw new InvalidIdException("Carrier Id is invalid");
			return optional.get();
	}

	public void deleteCarrier(int id) {
		carrierRepository.deleteById(id);
		
	}

	

}
