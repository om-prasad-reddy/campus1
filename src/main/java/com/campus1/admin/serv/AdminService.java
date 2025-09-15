package com.campus1.admin.serv;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campus1.admin.entity.Profile_Admin;

import com.campus1.admin.repo.AdminRepository;


@Service
public class AdminService {
    
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    
    private static final String DEFAULT_PROFILE_PICTURE = "data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNDAiIGhlaWdodD0iNDAiIHZpZXdCb3g9IjAgMCA0MCA0MCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPGNpcmNsZSBjeD0iMjAiIGN5PSIyMCIgcj0iMjAiIGZpbGw9IiM2Yjc0ZGIiLz4KPHN2ZyB4PSI4IiB5PSI4IiB3aWR0aD0iMjQiIGhlaWdodD0iMjQiIHZpZXdCb3g9IjAgMCAyNCAyNCIgZmlsbD0ibm9uZSI+CjxwYXRoIGQ9Ik0xMiAxMkM5Ljc5IDEyIDgsMTAuMjEgOCA4UzkuNzkgNCA1IDRTMTYgNS43OSAxNiA4UzE0LjIxIDEyIDEyIDEyWk0xMiAxNEM5LjMzIDE0IDQgMTUuMzQgNCAyMFYyMkgyMFYyMEMxNiAxNS4zNCAxNC42NyAxNCAxMiAxNVoiIGZpbGw9IndoaXRlIi8+Cjwvc3ZnPgo8L3N2Zz4K";
    
    private static final long MAX_IMAGE_SIZE = 2 * 1024 * 1024; // 2MB limit
    
    @Autowired
    private AdminRepository adminRepository;
    
   
    
    public Profile_Admin login(String email, String password) {
        Profile_Admin profile_Admin = adminRepository.findByEmail(email);
        if (profile_Admin == null) {
            throw new RuntimeException("Invalid credentials");
        }
        
        if (!profile_Admin.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }
        return profile_Admin;
    }
    
    public Map<String, Object> getAdminById(Long id) {
        Profile_Admin profile_Admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        String profilePictureUrl = DEFAULT_PROFILE_PICTURE;
        if (profile_Admin.getProfilePicture() != null && profile_Admin.getProfilePicture().length > 0) {
            profilePictureUrl = "data:" + profile_Admin.getProfilePictureType() + ";base64," + 
                Base64.getEncoder().encodeToString(profile_Admin.getProfilePicture());
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", profile_Admin.getId());
        response.put("fullName", profile_Admin.getFullName());
        response.put("email", profile_Admin.getEmail());
        response.put("phoneNumber", profile_Admin.getPhoneNumber());
        response.put("address", profile_Admin.getAddress());
        response.put("profilePicturePath", profilePictureUrl);
        return response;
    }
    
    public Profile_Admin updateAdminProfile(Long id, Profile_Admin request) {
        Profile_Admin profile_Admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        profile_Admin.setFullName(request.getFullName());
        profile_Admin.setEmail(request.getEmail());
        profile_Admin.setPhoneNumber(request.getPhoneNumber());
        profile_Admin.setAddress(request.getAddress());
        return adminRepository.save(profile_Admin);
    }
    
    public Map<String, Object> updateProfilePicture(Long id, String base64Data, String contentType) {
        Profile_Admin profile_Admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        // Check if we're deleting the profile picture
        if (base64Data == null) {
            profile_Admin.setProfilePicture(null);
            profile_Admin.setProfilePictureType(null);
        } 
        // Handle regular profile picture update
        else if (!base64Data.isEmpty()) {
            // Check if the base64 string has the data URI prefix
            String imageDataBytes;
            if (base64Data.contains(",")) {
                String[] parts = base64Data.split(",");
                if (parts.length < 2) {
                    throw new RuntimeException("Invalid base64 data format");
                }
                imageDataBytes = parts[1];
            } else {
                imageDataBytes = base64Data;
            }
            
            // Validate base64 string
            if (imageDataBytes.isEmpty()) {
                throw new RuntimeException("Empty base64 data");
            }
            
            // Decode base64 string
            byte[] imageBytes;
            try {
                imageBytes = Base64.getDecoder().decode(imageDataBytes);
            } catch (IllegalArgumentException e) {
                logger.error("Base64 decoding error: {}", e.getMessage());
                throw new RuntimeException("Invalid base64 data: " + e.getMessage());
            }
            
            // Validate image size
            if (imageBytes.length > MAX_IMAGE_SIZE) {
                throw new RuntimeException("Image size exceeds maximum limit of 2MB");
            }
            
            // Validate content type
            if (contentType == null || contentType.isEmpty() ||
                (!contentType.contains("jpeg") && !contentType.contains("jpg") && !contentType.contains("png"))) {
                throw new RuntimeException("Unsupported image type");
            }
            
            // Update admin with new image data (overwrites old data)
            profile_Admin.setProfilePicture(imageBytes);
            profile_Admin.setProfilePictureType(contentType);
        } else {
            throw new RuntimeException("No profile picture data provided");
        }
        
        Profile_Admin updatedAdmin = adminRepository.save(profile_Admin);
        logger.info("Profile picture updated successfully for admin ID: {}", id);
        
        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Profile picture updated successfully");
        
        // If we deleted the profile picture, return the default SVG
        if (updatedAdmin.getProfilePicture() == null) {
            response.put("profilePicturePath", DEFAULT_PROFILE_PICTURE);
        } else {
            response.put("profilePicturePath", "data:" + updatedAdmin.getProfilePictureType() + ";base64," + 
                Base64.getEncoder().encodeToString(updatedAdmin.getProfilePicture()));
        }
        
        return response;
    }
    
    public void changePassword(Long id, String currentPassword, String newPassword) {
        Profile_Admin profile_Admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        if (!profile_Admin.getPassword().equals(currentPassword)) {
            throw new RuntimeException("Current password is incorrect");
        }
        
        if (newPassword.length() < 6) {
            throw new RuntimeException("New password must be at least 6 characters");
        }
        
        profile_Admin.setPassword(newPassword);
        adminRepository.save(profile_Admin);
    }
    
   
}