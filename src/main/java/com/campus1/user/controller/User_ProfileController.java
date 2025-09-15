package com.campus1.user.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.campus1.user.entity.User;
import com.campus1.user.service.User_ProfileService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user/profile")
@RequiredArgsConstructor
@Slf4j
public class User_ProfileController {
 private final User_ProfileService user_ProfileService;
 private final ObjectMapper objectMapper;
 
 @GetMapping("/{userId}")
 public ResponseEntity<Map<String, Object>> getProfile(@PathVariable Long userId) {
     try {
         log.info("Fetching profile for user ID: {}", userId);
         User user = user_ProfileService.getProfileByUserId(userId);
         
         if (user == null) {
             log.warn("Profile not found for user ID: {}", userId);
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                     .body(Map.of("success", false, "message", "Profile not found"));
         }
         
         // Create a response map that will hold the profile data
         Map<String, Object> responseMap = new HashMap<>();
         
         // Copy all basic fields
         responseMap.put("id", user.getId());
         responseMap.put("firstName", user.getFirstName());
         responseMap.put("lastName", user.getLastName());
         responseMap.put("email", user.getEmail());
         responseMap.put("countryCode", user.getCountryCode());
         responseMap.put("mobile", user.getMobile());
         responseMap.put("dob", user.getDob());
         responseMap.put("gender", user.getGender());
         responseMap.put("address", user.getAddress());
         responseMap.put("fatherName", user.getFatherName());
         responseMap.put("motherName", user.getMotherName());
         responseMap.put("linkedinUrl", user.getLinkedinUrl());
         responseMap.put("githubUrl", user.getGithubUrl());
         responseMap.put("portfolioUrl", user.getPortfolioUrl());
         responseMap.put("objective", user.getObjective());
         
         // Set profile photo as binary data (will be converted to blob URL in frontend)
         responseMap.put("profilePhoto", user.getProfilePhoto() != null ? "has_photo" : null);
         
         // Set signature as binary data (will be converted to blob URL in frontend)
         responseMap.put("signature", user.getSignature() != null ? "has_signature" : null);
         
         // Convert collections
         responseMap.put("education", user.getEducation());
         responseMap.put("skills", user.getSkills());
         responseMap.put("experience", user.getExperience());
         responseMap.put("projects", user.getProjects());
         responseMap.put("achievements", user.getAchievements());
         responseMap.put("languages", user.getLanguages());
         responseMap.put("hobbies", user.getHobbies());
         responseMap.put("references", user.getReferences());
         
         // Process certifications - only include the three required fields
         if (user.getCertifications() != null) {
             List<Map<String, Object>> certList = new ArrayList<>();
             for (User.Certification cert : user.getCertifications()) {
                 Map<String, Object> certMap = new HashMap<>();
                 certMap.put("certificate_name", cert.getCertificateName());
                 certMap.put("issuing_organization", cert.getIssuingOrganization());
                 certMap.put("issue_date", cert.getIssueDate());
                 certList.add(certMap);
             }
             responseMap.put("certifications", certList);
         } else {
             responseMap.put("certifications", new ArrayList<>());
         }
         
         Map<String, Object> response = new HashMap<>();
         response.put("success", true);
         response.put("message", "Profile retrieved successfully");
         response.put("profile", responseMap);
         
         return ResponseEntity.ok(response);
     } catch (Exception e) {
         log.error("Error fetching profile for user ID: {}", userId, e);
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body(Map.of("success", false, "message", "Error retrieving profile: " + e.getMessage()));
     }
 }
 
 @PutMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
 public ResponseEntity<Map<String, Object>> updateProfile(
         @PathVariable Long userId,
         @RequestPart("profile") String profileJson,
         @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto,
         @RequestPart(value = "signature", required = false) MultipartFile signature) {
     try {
         log.info("Updating profile for user ID: {}", userId);
         
         // Parse the profile JSON
         JsonNode profileNode = objectMapper.readTree(profileJson);
         
         // Remove file fields from JSON to avoid deserialization issues
         if (profileNode.has("profilePhoto")) {
             ((ObjectNode) profileNode).remove("profilePhoto");
         }
         
         if (profileNode.has("signature")) {
             ((ObjectNode) profileNode).remove("signature");
         }
         
         // Convert back to User object
         User profileData = objectMapper.treeToValue(profileNode, User.class);
         
         User updatedUser = user_ProfileService.updateProfile(userId, profileData, profilePhoto, signature, null);
         
         log.info("Profile updated successfully for user ID: {}", userId);
         return ResponseEntity.ok(Map.of(
             "success", true,
             "message", "Profile updated successfully",
             "profile", updatedUser
         ));
     } catch (Exception e) {
         log.error("Error updating profile for user ID: {}", userId, e);
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body(Map.of("success", false, "message", "Error updating profile: " + e.getMessage()));
     }
 }
 
 @GetMapping("/photo/{userId}")
 public ResponseEntity<byte[]> getProfilePhoto(@PathVariable Long userId) {
     try {
         User user = user_ProfileService.getProfileByUserId(userId);
         if (user == null || user.getProfilePhoto() == null) {
             return ResponseEntity.notFound().build();
         }
         
         return ResponseEntity.ok()
                 .contentType(MediaType.IMAGE_JPEG)
                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"profile-photo.jpg\"")
                 .body(user.getProfilePhoto());
     } catch (Exception e) {
         log.error("Error loading profile photo for user ID: {}", userId, e);
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
     }
 }
 
 @GetMapping("/signature/{userId}")
 public ResponseEntity<byte[]> getSignature(@PathVariable Long userId) {
     try {
         User user = user_ProfileService.getProfileByUserId(userId);
         if (user == null || user.getSignature() == null) {
             return ResponseEntity.notFound().build();
         }
         
         return ResponseEntity.ok()
                 .contentType(MediaType.IMAGE_JPEG)
                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"signature.jpg\"")
                 .body(user.getSignature());
     } catch (Exception e) {
         log.error("Error loading signature for user ID: {}", userId, e);
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
     }
 }
}