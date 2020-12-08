package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.model.Address;
import com.cg.model.Customer;

//@RepositoryRestResource(collectionResourceRel = "customers", path = "customers")
public interface CustomerRepo extends JpaRepository<Customer, Long>{

	Customer findByUserName(String userName);
	
	@Query("select a from Address a where a.addressId = :id")
	Address findByAddress(long id);

}
