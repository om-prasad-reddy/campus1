package com.campus1.user.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;

@Service
public class User_FileStorageService {
    
    // Convert MultipartFile to byte array
    public byte[] storeFile(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException ex) {
            throw new RuntimeException("Could not process file. Please try again!", ex);
        }
    }
    
    // Convert byte array to base64 string
    public String convertToBase64(byte[] fileData, String fileName) {
        if (fileData == null) return null;
        
        String contentType = determineContentType(fileName);
        String base64 = Base64.getEncoder().encodeToString(fileData);
        return "data:" + contentType + ";base64," + base64;
    }
    
    // Convert base64 string to byte array
    public byte[] convertFromBase64(String base64String) {
        if (base64String == null || !base64String.contains(",")) {
            return null;
        }
        
        // Extract the base64 content after the comma
        String base64Content = base64String.split(",")[1];
        return Base64.getDecoder().decode(base64Content);
    }
    
    // Determine content type based on file extension
    private String determineContentType(String fileName) {
        if (fileName == null) return "application/octet-stream";
        
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "pdf":
                return "application/pdf";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            default:
                return "application/octet-stream";
        }
    }
}