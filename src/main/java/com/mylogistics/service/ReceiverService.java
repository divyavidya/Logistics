package com.mylogistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylogistics.model.Receiver;
import com.mylogistics.repository.ReceiverRepository;
@Service
public class ReceiverService {
@Autowired
private ReceiverRepository receiverRepository;
	public Receiver postReceiver(Receiver receiver) {
		// TODO Auto-generated method stub
		return receiverRepository.save(receiver);
	}

}
