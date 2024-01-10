package com.mylogistics.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mylogistics.model.Route;

public interface RouteRepository extends JpaRepository<Route, Integer>{

	@Query("select r from Route r where r.source=?1 and r.destination=?2")
	Optional<Route> getBySrcDest(String source, String destination);
	@Query("select distinct r.location from Route r where r.source=?1")
	List<String> getLocationBySource(String source);
	@Query("select distinct r.source from Route r")
	List<String> getACities();
	@Query(value = "SELECT r.location FROM Route r " +
		       "GROUP BY r.location " +
		       "ORDER BY MIN(r.source)")
	List<String> designatedLocations();
	

}
