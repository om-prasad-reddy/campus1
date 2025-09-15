package com.campus1.manager.dto;

import com.campus1.manager.model.ManagerStudents;

public class ManagerUserPieModelDto {
	private Long id;
    private String name;
    private String email;
    private String finalStatus;
    
    
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFinalStatus() {
		return finalStatus;
	}
	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}
	

//	public ManagerUserPieModelDto(ManagerUsers user) {
//	    this.id = user.getId();
//	    this.name = user.getName();
//	    this.email = user.getEmail();
//	    this.finalStatus = user.getFinalStatus();
//	}
	
	public ManagerUserPieModelDto(ManagerStudents user) {
	    this.id = user.getId();
	    this.name = user.getName();
	    this.email = user.getEmail();
	    this.finalStatus = user.getFinalStatus();
	}
	
}
