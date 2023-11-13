package com.mylogistics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mylogistics.model.Route;
import com.mylogistics.repository.RouteRepository;

@Service
public class RouteService {

	@Autowired
	private RouteRepository routeRepository;

	public Route postRoute(Route route) {
		return routeRepository.save(route);
	}

	public List<Route> getAll(Pageable pageable) {
		return routeRepository.findAll(pageable).getContent();
	}

}
