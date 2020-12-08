package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
	
//	public User signIn(User user);
//	public User signOut(User user);
//	public User changePassword(long id, User user);
	

	User findByUserName(String username);


}
