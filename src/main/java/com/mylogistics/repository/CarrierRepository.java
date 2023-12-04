package com.mylogistics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mylogistics.enums.CarrierStatus;
import com.mylogistics.model.Carrier;

public interface CarrierRepository extends JpaRepository<Carrier, Integer>{
	@Query("select c from Carrier c where c.city=?1 and c.status=?2")
	List<Carrier> getCarrier(String source, CarrierStatus available);

}
