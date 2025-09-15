package com.campus1.user.service;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.campus1.user.entity.User;
import com.campus1.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//Updated User_ProfileService.java
@Service
@RequiredArgsConstructor
@Slf4j
public class User_ProfileService {
 private final UserRepository userRepository;
 
 public User getProfileByUserId(Long userId) {
     log.info("Fetching profile for user ID: {}", userId);
     Optional<User> userOptional = userRepository.findById(userId);
     
     if (userOptional.isPresent()) {
         log.info("Profile found for user ID: {}", userId);
         return userOptional.get();
     } else {
         log.warn("Profile not found for user ID: {}", userId);
         return null;
     }
 }
 
 @Transactional
 public User updateProfile(Long userId, User profileData, 
                          MultipartFile profilePhoto, 
                          MultipartFile signature,
                          List<MultipartFile> certificates) {
     log.info("Updating profile for user ID: {}", userId);
     
     // Fetch existing user
     User existingUser = userRepository.findById(userId)
             .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
     
     // Update profile fields
     existingUser.setDob(profileData.getDob());
     existingUser.setGender(profileData.getGender());
     existingUser.setAddress(profileData.getAddress());
     existingUser.setFatherName(profileData.getFatherName());
     existingUser.setMotherName(profileData.getMotherName());
     existingUser.setLinkedinUrl(profileData.getLinkedinUrl());
     existingUser.setGithubUrl(profileData.getGithubUrl());
     existingUser.setPortfolioUrl(profileData.getPortfolioUrl());
     existingUser.setObjective(profileData.getObjective());
     
     // Handle profile photo upload
     if (profilePhoto != null && !profilePhoto.isEmpty()) {
         try {
             byte[] photoBytes = profilePhoto.getBytes();
             existingUser.setProfilePhoto(photoBytes);
         } catch (IOException e) {
             throw new RuntimeException("Failed to process profile photo", e);
         }
     }
     
     // Handle signature upload
     if (signature != null && !signature.isEmpty()) {
         try {
             byte[] signatureBytes = signature.getBytes();
             existingUser.setSignature(signatureBytes);
         } catch (IOException e) {
             throw new RuntimeException("Failed to process signature", e);
         }
     }
     
     // Handle certificate uploads
     if (certificates != null && !certificates.isEmpty() && profileData.getCertifications() != null) {
         for (int i = 0; i < certificates.size() && i < profileData.getCertifications().size(); i++) {
             MultipartFile certificateFile = certificates.get(i);
             if (certificateFile != null && !certificateFile.isEmpty()) {
                 User.Certification certification = profileData.getCertifications().get(i);
                 try {
                     byte[] certBytes = certificateFile.getBytes();
                     certification.setCertificateFile(certBytes);
                     certification.setCertificateFileName(certificateFile.getOriginalFilename());
                 } catch (IOException e) {
                     throw new RuntimeException("Failed to process certificate file", e);
                 }
             }
         }
     }
     
     // Update collections - preserve existing files if not updated
     if (profileData.getCertifications() != null) {
         // If we're updating certifications, we need to preserve existing files
         for (int i = 0; i < profileData.getCertifications().size(); i++) {
             User.Certification newCert = profileData.getCertifications().get(i);
             if (existingUser.getCertifications() != null && i < existingUser.getCertifications().size()) {
                 User.Certification existingCert = existingUser.getCertifications().get(i);
                 // If no new file is uploaded, keep the existing file
                 if (newCert.getCertificateFile() == null) {
                     newCert.setCertificateFile(existingCert.getCertificateFile());
                     newCert.setCertificateFileName(existingCert.getCertificateFileName());
                 }
             }
         }
         existingUser.setCertifications(profileData.getCertifications());
     }
     
     // Update other collections
     existingUser.setEducation(profileData.getEducation());
     existingUser.setSkills(profileData.getSkills());
     existingUser.setExperience(profileData.getExperience());
     existingUser.setProjects(profileData.getProjects());
     existingUser.setAchievements(profileData.getAchievements());
     existingUser.setLanguages(profileData.getLanguages());
     existingUser.setHobbies(profileData.getHobbies());
     existingUser.setReferences(profileData.getReferences());
     
     // Save updated user
     User savedUser = userRepository.save(existingUser);
     log.info("Profile updated successfully for user ID: {}", userId);
     
     return savedUser;
 }
 
 public Optional<User> findById(Long id) {
     return userRepository.findById(id);
 }
}