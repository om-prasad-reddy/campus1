package com.campus1.manager.dto;

public class ManagerAdminDto {
    private Long id;
    private String name;
    private String email;
    private String phone; // Changed from Long to String to match ManagerAdmin
    private String designation;
    private String address;
    private long recruitsCount; // Changed to long to match the constructor call

    // Constructor matching the call in ManagerManagerService
    public ManagerAdminDto(Long id, String name, String email, String phone, String designation, String address, long recruitsCount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.designation = designation;
        this.address = address;
        this.recruitsCount = recruitsCount;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getRecruitsCount() {
        return recruitsCount;
    }

    public void setRecruitsCount(long recruitsCount) {
        this.recruitsCount = recruitsCount;
    }
}