package com.campus1.manager.dto;

import java.util.List;



public class ManagerAssignTaskDto {
	
	private String managerEmail;
    private Long adminId;
    private List<Long> userIds;

    public ManagerAssignTaskDto() {} 

    

    public Long getAdminId() {
        return adminId;
    }
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public List<Long> getUserIds() {
        return userIds;
    }
    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }



	public String getManagerEmail() {
		return managerEmail;
	}



	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
    
}
