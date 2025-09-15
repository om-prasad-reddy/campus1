package com.campus1.admin.dto;



import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}