package com.cg.service;

import java.util.List;

import com.cg.model.SalonService;

public interface ISalonService {
	public SalonService addService(SalonService salonService);
	public boolean removeService(long id);
	public SalonService updateService(SalonService salonService);
	public SalonService getService(long id);
	public List<SalonService> getAllServices();
	public List<SalonService> getServiceByPrice(double min, double max);
	public List<SalonService> getServicebyName(String serviceName);
	public List<SalonService> getServiceByDuration(String duration);
}
