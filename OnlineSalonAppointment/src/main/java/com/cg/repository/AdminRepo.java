package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.model.Admin;

//@RepositoryRestResource(collectionResourceRel = "admins", path="admins")
public interface AdminRepo extends JpaRepository<Admin, Long>{

	Admin findByEmailId(String email);

	Admin findByUserName(String userName);
	
//	
//	List<Admin> findAllByUserName();

}
