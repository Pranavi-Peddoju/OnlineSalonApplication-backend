package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.model.Order;

public interface IOrderRepository extends JpaRepository<Order, Long>{

	

}
