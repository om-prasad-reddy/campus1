package com.campus1.manager.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ManagerNotificationResponseDto {
	 	private Long id;
	    private String subject;
	    private String message;
	    private String sentBy;
	    private String sentRole;
	    private LocalDateTime sentAt;
	    private boolean isRead;
	    private String priority;
	    
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
		public boolean isRead() {
			return isRead;
		}
		public void setRead(boolean isRead) {
			this.isRead = isRead;
		}
		
		
		public String getSentRole() {
			return sentRole;
		}
		public void setSentRole(String sentRole) {
			this.sentRole = sentRole;
		}
		public ManagerNotificationResponseDto(Long id, String subject, String message, String sentBy, LocalDateTime sentAt,
				String sentRole,boolean isRead,String priority) {
			super();
			this.id = id;
			this.subject = subject;
			this.message = message;
			this.sentBy = sentBy;
			this.sentAt = sentAt;
			this.isRead = isRead;
			this.sentRole=sentRole;
			this.priority=priority;
		}
		public ManagerNotificationResponseDto() {
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			return "ManagerNotificationResponseDto [id=" + id + ", subject=" + subject + ", message=" + message + ", sentBy="
					+ sentBy + ", sentAt=" + sentAt + ", isRead=" + isRead + ", getId()=" + getId() + ", getSubject()="
					+ getSubject() + ", getMessage()=" + getMessage() + ", getSentBy()=" + getSentBy()
					+ ", getSentAt()=" + getSentAt() + ", isRead()=" + isRead() + ", getClass()=" + getClass()
					+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
		}
		public String getPriority() {
			return priority;
		}
		public void setPriority(String priority) {
			this.priority = priority;
		}	
}
