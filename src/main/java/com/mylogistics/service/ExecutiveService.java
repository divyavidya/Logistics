package com.mylogistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylogistics.model.Executive;
import com.mylogistics.repository.ExecutiveRepository;

@Service
public class ExecutiveService {
@Autowired
private ExecutiveRepository executiveRepository;
	public Executive postExecutive(Executive executive) {
		// TODO Auto-generated method stub
		return executiveRepository.save(executive);
	}

}
