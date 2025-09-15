package com.campus1.manager.dto;

public class ManagerLoginResponseDto {
	
	private String status;
    private String message;
    private String email;

    
    
    public ManagerLoginResponseDto() {}
    
    
    
	public ManagerLoginResponseDto(String status, String message, String email) {
		super();
		this.status = status;
		this.message = message;
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
}
