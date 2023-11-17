package com.mylogistics.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mylogistics.exception.InvalidIdException;
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

	public Route getBySrcDest(String source, String destination) throws InvalidIdException  {
		 Optional<Route> optional = routeRepository.getBySrcDest(source,destination);
		 if(!optional.isPresent())
				throw new InvalidIdException("source/destination invalid");
			return optional.get();
	
	}

	public Route getRouteById(int rid) throws InvalidIdException {
		Optional<Route> optional = routeRepository.findById(rid);
		 if(!optional.isPresent())
				throw new InvalidIdException("Route id invalid");
			return optional.get();
	}

	public void deleteRoute(int id) {
		routeRepository.deleteById(id);
		
	}

}
