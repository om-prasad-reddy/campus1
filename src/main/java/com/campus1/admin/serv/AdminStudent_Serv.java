package com.campus1.admin.serv;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.campus1.admin.entity.Admin_Tracking_System;
import com.campus1.admin.repo.AdminStudentRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class AdminStudent_Serv {
    @Autowired
    private AdminStudentRepo adminStudentRepo;
    @Autowired
    private JavaMailSender mailSender;
    
    // Get all students
    public List<Admin_Tracking_System> getAllStudents() {
        return adminStudentRepo.findAll();
    }
    
    public List<Admin_Tracking_System> getStudentsByAdmin(Long adminId) {
        return adminStudentRepo.findByProfileAdminId(adminId);
    }
    
    // Get student by ID
    public Admin_Tracking_System getStudentById(Long id) {
        return adminStudentRepo.findById(id).orElse(null);
    }
    
    // Create new student
    public Admin_Tracking_System createStudent(Admin_Tracking_System student) {
        try {
            return adminStudentRepo.save(student);
        } catch (Exception e) {
            throw new RuntimeException("Email already exists.");
        }
    }
    
    // Update student
    public Admin_Tracking_System updateStudent(Long id, Admin_Tracking_System updatedStudent) {
        Optional<Admin_Tracking_System> optionalStudent = adminStudentRepo.findById(id);
        if (optionalStudent.isPresent()) {
            Admin_Tracking_System student = optionalStudent.get();
            student.setName(updatedStudent.getName());
            student.setEmail(updatedStudent.getEmail());
            student.setDepartment(updatedStudent.getDepartment());
            student.setQualification(updatedStudent.getQualification());
            student.setAge(updatedStudent.getAge());
            try {
                return adminStudentRepo.save(student);
            } catch (Exception e) {
                throw new RuntimeException("Email already exists.");
            }
        } else {
            throw new RuntimeException("Student not found with ID: " + id);
        }
    }
    
    // Delete student
    public String deleteStudentById(Long id) {
        if (adminStudentRepo.existsById(id)) {
            adminStudentRepo.deleteById(id);
            return "Deleted Student ID: " + id;
        } else {
            return "Student not found.";
        }
    }
    
    // Send HTML Email
    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // 'true' for multipart email
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // Enable HTML
        
        // Only add the image if the file exists
        File imageFile = new File("src/main/resources/static/images/cye.png");
        if (imageFile.exists()) {
            FileSystemResource res = new FileSystemResource(imageFile);
            helper.addInline("cye.png", res); // ID must match 'cid:cye.png' in your HTML
        }
        
        mailSender.send(mimeMessage);
        System.out.println("Email sent successfully to: " + to + " with subject: " + subject);
    }
    
    public Admin_Tracking_System save(Admin_Tracking_System student) {
        return adminStudentRepo.save(student);
    }
    
    public Optional<Admin_Tracking_System> findById(Long id) {
        return adminStudentRepo.findById(id);
    }
    
    public void sendStatusEmails(Admin_Tracking_System student, Admin_Tracking_System dto) throws MessagingException {
        String to = student.getEmail();
        String name = student.getName();
        if (to == null || name == null) {
            System.out.println("Skipping email: to or name is null for student ID: " + student.getId());
            return;
        }
        
        System.out.println("Processing status emails for student: " + name + " (" + to + ")");
        
        // Call Status emails - Only for positive statuses
        if (!equalsIgnoreCase(student.getCallStatus(), dto.getCallStatus())) {
            System.out.println("Call status changed from '" + student.getCallStatus() + "' to '" + dto.getCallStatus() + "'");
            if ("answered".equalsIgnoreCase(dto.getCallStatus())) {
                System.out.println("Sending 'Answered' email to: " + to);
                sendHtmlEmail(to, "Screening Call Update",
                    generateHtml(name,
                        "Thank you for attending the call. Our team will review the discussion and get in touch with you for " +
                        "the next steps in the selection process.<br>" +
                        "Please keep an eye on your email for further communication."
                    ));
            } else if ("answered-busy".equalsIgnoreCase(dto.getCallStatus())) {
                System.out.println("Sending 'Answered-Busy' email to: " + to);
                sendHtmlEmail(to, "Call Status Update - Answered but Busy",
                    generateHtml(name,
                        "We were able to reach you but you were busy at the moment. " +
                        "Please let us know a convenient time for a follow-up call."
                    ));
            }
        }
        
        // REMOVED: Job Status emails - No longer sending emails for "Looking for Job"
        
        // REMOVED: Eligibility Status emails - No longer sending emails for "Eligible for client"
        
        // REMOVED: Client Status emails - No longer sending emails for any client status
        
        // Interview Status emails - Only for positive statuses
        if (!equalsIgnoreCase(student.getTelephoneInterview(), dto.getTelephoneInterview())) {
            System.out.println("Telephone interview status changed from '" + student.getTelephoneInterview() + "' to '" + dto.getTelephoneInterview() + "'");
            if ("selected".equalsIgnoreCase(dto.getTelephoneInterview())) {
                System.out.println("Sending 'Telephone Interview Selected' email to: " + to);
                sendHtmlEmail(to, "Telephone Interview Selected",
                    generateHtml(name, 
                        "We are pleased to inform you that you have successfully cleared the Telephonic Interview Round. " +
                        "Congratulations on your achievement! Our HR team will be reaching out to you shortly with further details."
                    ));
            }
        }
        
        // HR Interview emails - Only for positive statuses
        if (!equalsIgnoreCase(student.getHrInterview(), dto.getHrInterview())) {
            System.out.println("HR interview status changed from '" + student.getHrInterview() + "' to '" + dto.getHrInterview() + "'");
            if ("selected".equalsIgnoreCase(dto.getHrInterview())) {
                System.out.println("Sending 'HR Interview Selected' email to: " + to);
                sendHtmlEmail(to, "HR Interview Selected",
                    generateHtml(name, 
                        "Congratulations! You have successfully completed the HR Interview. " +
                        "We appreciate the time and effort you invested in the process. " +
                        "Our team will now review your interview performance and get back to you soon."
                    ));
            }
        }
        
        // Operation Round emails - Only for positive statuses
        if (!equalsIgnoreCase(student.getOperationRound(), dto.getOperationRound())) {
            System.out.println("Operation round status changed from '" + student.getOperationRound() + "' to '" + dto.getOperationRound() + "'");
            if ("selected".equalsIgnoreCase(dto.getOperationRound())) {
                System.out.println("Sending 'Operation Round Selected' email to: " + to);
                sendHtmlEmail(to, "🎉 Congratulations! Operation Round Selection",
                    generateHtml(name,
                        "Congratulations! You have been <strong style='color:green;'>successfully selected</strong> for the <strong>Operation Round</strong>.<br><br>" +
                        "We appreciate your efforts so far. Please wait for further updates regarding the schedule and next steps.<br>" +
                        "Stay tuned and keep checking your email regularly."
                    ));
            } else if ("ops 2nd chance".equalsIgnoreCase(dto.getOperationRound())) {
                System.out.println("Sending 'Operation Round 2nd Chance' email to: " + to);
                sendHtmlEmail(to, "Operation Round - Second Chance",
                    generateHtml(name,
                        "We are providing you with a second chance for the Operation Round. " +
                        "Please prepare well and make the most of this opportunity."
                    ));
            }
        }
        
        // Versant Voice emails - Only for positive statuses
        if (!equalsIgnoreCase(student.getVersantVoice(), dto.getVersantVoice())) {
            System.out.println("Versant voice status changed from '" + student.getVersantVoice() + "' to '" + dto.getVersantVoice() + "'");
            if ("selected".equalsIgnoreCase(dto.getVersantVoice())) {
                System.out.println("Sending 'Versant Voice Selected' email to: " + to);
                sendHtmlEmail(to, "Versant Voice Round Selected",
                    generateHtml(name,
                        "Congratulations! You have successfully cleared the Versant Voice Round. " +
                        "Our team will contact you for the next steps."
                    ));
            } else if ("2nd attempt".equalsIgnoreCase(dto.getVersantVoice())) {
                System.out.println("Sending 'Versant Voice 2nd Attempt' email to: " + to);
                sendHtmlEmail(to, "Versant Voice Round - Second Attempt",
                    generateHtml(name,
                        "We are providing you with a second attempt for the Versant Voice Round. " +
                        "Please prepare well and make the most of this opportunity."
                    ));
            }
        }
        
        // Documentation emails - Only for positive statuses
        if (!equalsIgnoreCase(student.getDocumentation(), dto.getDocumentation())) {
            System.out.println("Documentation status changed from '" + student.getDocumentation() + "' to '" + dto.getDocumentation() + "'");
            if ("submitted".equalsIgnoreCase(dto.getDocumentation())) {
                System.out.println("Sending 'Documentation Submitted' email to: " + to);
                sendHtmlEmail(to, "Documentation Submitted",
                    generateHtml(name, "We have received your <strong>Documentation</strong>. It is currently under review."));
            }
        }
        
        // Offer Letter emails - Only for positive statuses
        if (!equalsIgnoreCase(student.getOfferLetter(), dto.getOfferLetter())) {
            System.out.println("Offer letter status changed from '" + student.getOfferLetter() + "' to '" + dto.getOfferLetter() + "'");
            if ("released".equalsIgnoreCase(dto.getOfferLetter())) {
                System.out.println("Sending 'Offer Letter Released' email to: " + to);
                sendHtmlEmail(to, "Offer Letter Released",
                    generateHtml(name, 
                        "We are pleased to inform you that your offer letter has been released. " +
                        "Please check your email for the detailed offer document and follow the instructions provided."
                    ));
            }
        }
        
        // Joining emails - Only for positive statuses
        if (!equalsIgnoreCase(student.getJoining(), dto.getJoining())) {
            System.out.println("Joining status changed from '" + student.getJoining() + "' to '" + dto.getJoining() + "'");
            if ("joined".equalsIgnoreCase(dto.getJoining())) {
                System.out.println("Sending 'Joined' email to: " + to);
                sendHtmlEmail(to, "Welcome Aboard!",
                    generateHtml(name, 
                        "We are delighted to welcome you to our organization! " +
                        "We hope you have a rewarding and successful career with us. " +
                        "Please reach out if you need any assistance during your onboarding process."
                    ));
            }
        }
    }
    
    // Utility method to compare ignoring nulls
    private boolean equalsIgnoreCase(String a, String b) {
        if (a == null && b == null)
            return true;
        if (a == null || b == null)
            return false;
        return a.equalsIgnoreCase(b);
    }
    
    private String generateHtml(String name, String message) {
        return "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<title>CYE TECHNOLOGY PRIVATE LIMITED Notification</title>" +
                "</head>" +
                "<body style='font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f9f9f9;'>" +
                "<table width='100%' cellpadding='0' cellspacing='0' style='max-width: 600px; margin: 20px auto; background-color: #ffffff; border: 1px solid #e0e0e0;'>" +
                "<tr>" +
                "<td style='padding: 20px; text-align: center;'>" +
                "<img src='cid:cye.png' alt='CYE TECHNOLOGY PRIVATE LIMITED Logo' " +
                "style='max-width: 150px; height: auto; display: block; margin: 0 auto;'>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 20px;'>" +
                "<h2 style='color: #333333; font-size: 20px; margin: 0 0 20px;'>Dear " + name + ",</h2>" +
                "<p style='color: #555555; line-height: 1.5; margin: 0 0 15px;'>" + message + "</p>" +
                "<p style='color: #555555; line-height: 1.5; margin: 0 0 15px;'>If you have any questions or need assistance, please contact our support team at " +
                "<a href='mailto:support@cyetechnology.in' style='color: #003087; text-decoration: none;'>support@cyetechnology.in</a>.</p>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 15px; text-align: center; background-color: #f1f1f1;'>" +
                "<p style='color: #777777; font-size: 12px; margin: 0;'>Best Regards,<br>CYE TECHNOLOGY PRIVATE LIMITED Team</p>" +
                "<p style='color: #777777; font-size: 12px; margin: 10px 0 0;'>" +
                "<a href='https://www.cyetechnology.com' style='color: #003087; text-decoration: none;'>www.cyetechnology.com</a></p>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</body>" +
                "</html>";
    }
    
    // Update status with final status logic
    public Admin_Tracking_System updateStatusWithFinalStatus(Admin_Tracking_System student) {
        // Check if there's a rejection at any stage
        boolean isRejected = isRejectedAtAnyStage(student);
        // Check if joining status is "Joined"
        boolean isJoined = "Joined".equalsIgnoreCase(student.getJoining());
        
        if (isRejected) {
            // If there's any rejection, set final status to "Rejected" and date to today
            student.setFinalStatus("Rejected");
            student.setFinalStatusDate(LocalDate.now());
        } else if (isJoined) {
            // If joined, set final status to "Recruited"
            student.setFinalStatus("Recruited");
            // If finalStatusDate is not set, set it to today (user can change it later)
            if (student.getFinalStatusDate() == null) {
                student.setFinalStatusDate(LocalDate.now());
            }
        } else {
            // If no rejection and not joined yet, clear final status and date
            student.setFinalStatus(null);
            student.setFinalStatusDate(null);
        }
        
        return adminStudentRepo.save(student);
    }
    
    // Helper method to check if student is rejected at any stage
    private boolean isRejectedAtAnyStage(Admin_Tracking_System student) {
        return "Not Answered".equalsIgnoreCase(student.getCallStatus()) ||
               "Not Looking for Job".equalsIgnoreCase(student.getLookingForJob()) ||
               "Not Eligible Client".equalsIgnoreCase(student.getEligibleForClient()) ||
               "Rejected".equalsIgnoreCase(student.getTelephoneInterview()) ||
               "Rejected".equalsIgnoreCase(student.getHrInterview()) ||
               "Rejected".equalsIgnoreCase(student.getOperationRound()) ||
               "Rejected".equalsIgnoreCase(student.getVersantVoice()) ||
               "Offer Rejected".equalsIgnoreCase(student.getOfferLetter()) ||
               "Not Joined".equalsIgnoreCase(student.getJoining());
    }
}