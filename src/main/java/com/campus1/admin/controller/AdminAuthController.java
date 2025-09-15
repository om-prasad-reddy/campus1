package com.campus1.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campus1.admin.entity.Profile_Admin;

import com.campus1.admin.serv.AdminService;

@RestController
@RequestMapping("/api/admin/auth")
@CrossOrigin("*")
public class AdminAuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AdminAuthController.class);
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            Profile_Admin profile_Admin = adminService.login(request.get("email"), request.get("password"));
            Map<String, Object> response = new HashMap<>();
            response.put("id", profile_Admin.getId());
            response.put("fullName", profile_Admin.getFullName());
            response.put("email", profile_Admin.getEmail());
            response.put("phoneNumber", profile_Admin.getPhoneNumber());
            response.put("address", profile_Admin.getAddress());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/admin/{id}")
    public ResponseEntity<?> getAdminById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.getAdminById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/admin/{id}")
    public ResponseEntity<?> updateAdminProfile(@PathVariable Long id, @RequestBody Profile_Admin request) {
        try {
            Profile_Admin updatedAdmin = adminService.updateAdminProfile(id, request);
            Map<String, Object> response = new HashMap<>();
            response.put("id", updatedAdmin.getId());
            response.put("fullName", updatedAdmin.getFullName());
            response.put("email", updatedAdmin.getEmail());
            response.put("phoneNumber", updatedAdmin.getPhoneNumber());
            response.put("address", updatedAdmin.getAddress());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/admin/{id}/profile-picture")
    public ResponseEntity<?> updateProfilePicture(
            @PathVariable Long id, 
            @RequestBody Map<String, String> request) {
        try {
            logger.info("Received profile picture update request for admin ID: {}", id);
            Map<String, Object> response = adminService.updateProfilePicture(id, 
                request.get("profilePicture"), 
                request.get("profilePictureType"));
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.error("Exception updating profile picture: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error updating profile picture: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(Map.of(
                "error", "Failed to update profile picture: " + e.getMessage()
            ));
        }
    }
    
    @PutMapping("/admin/{id}/password")
    public ResponseEntity<?> changePassword(
            @PathVariable Long id, 
            @RequestBody Map<String, String> request) {
        try {
            adminService.changePassword(id, 
                request.get("currentPassword"), 
                request.get("newPassword"));
            return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            logger.error("Exception changing password: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(Map.of(
                "error", "Failed to change password: " + e.getMessage()
            ));
        }
    }
    
}