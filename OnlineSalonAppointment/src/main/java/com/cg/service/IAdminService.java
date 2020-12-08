package com.cg.service;

import java.util.List;

import com.cg.model.Admin;

public interface IAdminService {

	public Admin getByUserName(String userName);
	public Admin updateAdmin(Admin admin);
	public List<Admin> getAllAdmins();
	public Admin getAdminById(long userId);
	public boolean removeAdmin(long userId);
	public Admin addAdmin(Admin admin);
}
