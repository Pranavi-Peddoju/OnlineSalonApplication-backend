package com.cg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.model.SalonService;

public interface ISalonRepository extends JpaRepository<SalonService, Long>{
	
	public List<SalonService> findByPriceBetweenOrderByPrice(double min, double max);
	
	public List<SalonService> findByDurationOrderByServiceId(String duration);

	public List<SalonService> findByServiceNameIgnoreCase(String serviceName);
	
	
}

