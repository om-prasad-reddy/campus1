package com.campus1.admin.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.campus1.admin.entity.Profile_Admin;
import com.campus1.admin.entity.Admin_Tracking_System;
import com.campus1.admin.repo.AdminRepository;
import com.campus1.admin.serv.AdminStudent_Serv;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/student")
@CrossOrigin("*")
public class AdminStudent_Controller {
    @Autowired
    private AdminStudent_Serv studentserv;
    
    @Autowired
    private AdminRepository managerRepo;

    @GetMapping("/all")
    public ResponseEntity<?> Task(@RequestParam String mail) {
        try {
            Profile_Admin lis = managerRepo.findByEmail(mail);
            if (lis != null) {
                List<Admin_Tracking_System> user = studentserv.getStudentsByAdmin(lis.getId());
                System.out.println("Found " + user.size() + " students for admin: " + mail);
                return ResponseEntity.ok(user);
            } else {
                System.out.println("Admin not found with email: " + mail);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found with email: " + mail);
            }
        } catch (Exception e) {
            System.err.println("Error fetching students: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching students: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin_Tracking_System> getById(@PathVariable Long id) {
        try {
            Admin_Tracking_System student = studentserv.getStudentById(id);
            if (student != null) {
                return ResponseEntity.ok(student);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            System.err.println("Error fetching student by ID: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Admin_Tracking_System> createStudent(@Valid @RequestBody Admin_Tracking_System student) {
        try {
            Admin_Tracking_System createdStudent = studentserv.createStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } catch (Exception e) {
            System.err.println("Error creating student: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin_Tracking_System> updateStudent(@PathVariable Long id, @Valid @RequestBody Admin_Tracking_System student) {
        try {
            Admin_Tracking_System updatedStudent = studentserv.updateStudent(id, student);
            if (updatedStudent != null) {
                return ResponseEntity.ok(updatedStudent);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            System.err.println("Error updating student: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        try {
            String result = studentserv.deleteStudentById(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.err.println("Error deleting student: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting student: " + e.getMessage());
        }
    }

    @PostMapping("/send-mail/{id}")
    public ResponseEntity<String> sendMail(@PathVariable Long id, @RequestBody EmailRequest emailRequest) {
        try {
            Admin_Tracking_System student = studentserv.getStudentById(id);
            if (student == null) {
                return ResponseEntity.badRequest().body("❌ Student not found with ID: " + id);
            }
            
            String subject = emailRequest.getSubject() != null ? emailRequest.getSubject() : "Welcome to Our Portal!";
            String html = emailRequest.getHtml() != null ? emailRequest.getHtml() : "<html><body>" +
                    "<h3 style='color: #1abc9c;'>Hi " + student.getName() + "!</h3>" +
                    "<p>This is a personalized message from admin.</p>" +
                    "<p>Regards,<br>Admin Team</p>" +
                    "</body></html>";
            
            studentserv.sendHtmlEmail(student.getEmail(), subject, html);
            return ResponseEntity.ok("Email sent to " + student.getEmail());
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Error sending email: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error sending email: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Unexpected error: " + e.getMessage());
        }
    }

    @PutMapping("/save-status/{id}")
    public ResponseEntity<String> saveFullStatus(@PathVariable Long id, @RequestBody Admin_Tracking_System dto) {
        try {
            System.out.println("Saving status for student ID: " + id);
            Optional<Admin_Tracking_System> optionalStudent = studentserv.findById(id);
            if (!optionalStudent.isPresent()) {
                System.err.println("Student not found with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
            }
            
            Admin_Tracking_System student = optionalStudent.get();
            System.out.println("Current student data: " + student);
            System.out.println("New status data: " + dto);
            
            try {
                studentserv.sendStatusEmails(student, dto);
            } catch (MessagingException e) {
                System.err.println("Error sending status emails: " + e.getMessage());
                // Continue with status update even if email fails
            }
            
            // Update the fields
            if (dto.isClosed()) {
                student.setClosed(true);
            } else {
                student.setCallStatus(dto.getCallStatus());
                student.setAnsweredCall(dto.getAnsweredCall());
                student.setLookingForJob(dto.getLookingForJob());
                student.setEligibleForClient(dto.getEligibleForClient());
                student.setTelephoneInterview(dto.getTelephoneInterview());
                student.setHrInterview(dto.getHrInterview());
                student.setOperationRound(dto.getOperationRound());
                student.setVersantVoice(dto.getVersantVoice());
                student.setDocumentation(dto.getDocumentation());
                student.setOfferLetter(dto.getOfferLetter());
                student.setJoining(dto.getJoining());
                // Update final status fields
                student.setFinalStatus(dto.getFinalStatus());
                student.setFinalStatusDate(dto.getFinalStatusDate());
                student.setClosed(false);
            }
            
            // Apply final status logic
            studentserv.updateStatusWithFinalStatus(student);
            
            System.out.println("Student status updated successfully for ID: " + id);
            return ResponseEntity.ok("Student status updated successfully");
        } catch (Exception e) {
            System.err.println("Error saving student status: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating student status: " + e.getMessage());
        }
    }

    // New endpoint for reopening a closed profile
    @PutMapping("/reopen/{id}")
    public ResponseEntity<String> reopenStudent(@PathVariable Long id) {
        try {
            Optional<Admin_Tracking_System> optionalStudent = studentserv.findById(id);
            if (!optionalStudent.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
            }
            
            Admin_Tracking_System student = optionalStudent.get();
            if (!student.isClosed()) {
                return ResponseEntity.badRequest().body("Student is not closed");
            }
            
            student.setClosed(false);
            studentserv.save(student);
            
            return ResponseEntity.ok("Student profile reopened and moved to ASSIGNED");
        } catch (Exception e) {
            System.err.println("Error reopening student: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reopening student: " + e.getMessage());
        }
    }
    
    // Helper class for email request
    public static class EmailRequest {
        private String subject;
        private String html;
        
        public String getSubject() {
            return subject;
        }
        
        public void setSubject(String subject) {
            this.subject = subject;
        }
        
        public String getHtml() {
            return html;
        }
        
        public void setHtml(String html) {
            this.html = html;
        }
    }
}