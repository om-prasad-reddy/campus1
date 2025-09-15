package com.campus1.manager.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.campus1.manager.model.ManagerUsers;
import com.campus1.manager.repository.ManagerUserRepository;




@Service
public class ManagerUserService {
	
	private final ManagerUserRepository usersRepo;
	
	
	public ManagerUserService(ManagerUserRepository usersRepo ) {
		this.usersRepo=usersRepo;
	}
	
    public List<ManagerUsers> getTasksAssignedToManager(Long managerId) {
        return usersRepo.findByManagerIdAndAdminIsNull(managerId);
    }
    
    public List<ManagerUsers> getTaskAssignedToManagerToAdmin(Long managerid){
    	return usersRepo.findByManagerIdAndAdminIsNotNull(managerid);
    }
}
