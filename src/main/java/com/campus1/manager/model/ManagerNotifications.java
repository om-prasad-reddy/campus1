package com.campus1.manager.model;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class ManagerNotifications {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    
    @Column(length = 2000)
    private String message;

    @Column(name="sent_by")
    private String sentBy;
    
    @Column(name="sent_at")
    private LocalDateTime sentAt;
    
    @Column(name="sent_role")
    private String sentRole;
    
    
    private String priority;

    @OneToMany(mappedBy = "notifications", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ManagerNotificationAdmin> adminRecipients = new HashSet<>();

    @OneToMany(mappedBy = "notifications", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ManagerNotificationManager> managerRecipients = new HashSet<>();

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

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}

	public Set<ManagerNotificationAdmin> getAdminRecipients() {
		return adminRecipients;
	}

	public void setAdminRecipients(Set<ManagerNotificationAdmin> adminRecipients) {
		this.adminRecipients = adminRecipients;
	}

	public Set<ManagerNotificationManager> getManagerRecipients() {
		return managerRecipients;
	}

	public void setManagerRecipients(Set<ManagerNotificationManager> managerRecipients) {
		this.managerRecipients = managerRecipients;
	}

	public String getSentRole() {
		return sentRole;
	}

	public void setSentRole(String setRole) {
		this.sentRole = setRole;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
}