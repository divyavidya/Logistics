package com.mylogistics.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mylogistics.model.Route;

public interface RouteRepository extends JpaRepository<Route, Integer>{

	@Query("select r from Route r where r.source=?1 and r.destination=?2")
	Optional<Route> getBySrcDest(String source, String destination);

}
