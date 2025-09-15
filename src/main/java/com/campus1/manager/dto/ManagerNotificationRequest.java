package com.campus1.manager.dto;

import java.util.List;

public class ManagerNotificationRequest {
	private String subject;
    private String message;
    private String sentBy;
    private String priority;
    private List<Long> adminIds;
   
    
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSentBy() {
		return sentBy;
	}
	public void setSentBy(String sentBy) {
		this.sentBy = sentBy;
	}
	public List<Long> getAdminIds() {
		return adminIds;
	}
	public void setAdminIds(List<Long> adminIds) {
		this.adminIds = adminIds;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
}
