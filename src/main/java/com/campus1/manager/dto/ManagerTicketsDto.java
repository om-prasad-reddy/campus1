package com.campus1.manager.dto;

import java.time.LocalDateTime;

public class ManagerTicketsDto {
	private Long id;
    private String subject;
    private String category;
    private String priority;
    private String description;
    private String status;
    private String decision;
    private LocalDateTime createdAt;
    private String managerEmail;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getManagerEmail() {
		return managerEmail;
	}
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	@Override
	public String toString() {
		return "ManagerTicketsDto [id=" + id + ", subject=" + subject + ", category=" + category + ", priority=" + priority
				+ ", description=" + description + ", status=" + status + ", decision=" + decision + ", createdAt="
				+ createdAt + ", managerEmail=" + managerEmail + "]";
	}
	
	
}
