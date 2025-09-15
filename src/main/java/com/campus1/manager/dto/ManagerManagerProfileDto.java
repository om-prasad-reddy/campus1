package com.campus1.manager.dto;


public class ManagerManagerProfileDto {
    private Long id;
    private String name;
    private String email;
    private Long phone;
    private String role;
    private String address;
    private String imageBase64;
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
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImageBase64() {
		return imageBase64;
	}
	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}
	
	public ManagerManagerProfileDto(Long id, String name, String email, Long phone, String role, String address,
			String imageBase64) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.role = role;
		this.address = address;
		this.imageBase64 = imageBase64;
	}
	public ManagerManagerProfileDto() {
		// TODO Auto-generated constructor stub
	}
}
