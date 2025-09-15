package com.campus1.admin.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.campus1.admin.dto.ScheduleRequest;
import com.campus1.admin.serv.AdminCalendarService;
import com.campus1.admin.serv.AdminEmailService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin/interview")
@Validated
public class AdminInterviewController {

	private static final Logger logger = LoggerFactory.getLogger(AdminInterviewController.class);

	@Autowired
	private AdminEmailService adminEmailService;

	@Autowired
	private AdminCalendarService adminCalendarService;

	@PostMapping("/schedule/{id}")
    public ResponseEntity<String> scheduleInterviewById(@PathVariable Long id) {
        try {
            ScheduleRequest request = adminCalendarService.getScheduleRequestById(id);

            // Schedule in DB / Google Calendar
            String confirmationLink = adminCalendarService.scheduleAndGenerateAuthLink(request);

            // Send email
            adminEmailService.sendInterviewEmail(request, confirmationLink);

            return ResponseEntity.ok("Interview scheduled and email sent to " + request.getCandidateEmail());
        } catch (Exception e) {
            logger.error("Failed to schedule interview by ID: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body("Failed to schedule interview: " + e.getMessage());
        }
    }
	@GetMapping("/callback")
	public ResponseEntity<String> oauth2Callback(@RequestParam("code") String code,
			@RequestParam("state") String state) {
		logger.info("Received OAuth2 callback with state: {}", state);
		try {
			adminCalendarService.createCalendarEvent(code, state);
			return ResponseEntity.ok("Event successfully added to Google Calendar");
		} catch (Exception e) {
			logger.error("Failed to add event to calendar: {}", e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Failed to add event: " + e.getMessage());
		}
	}
	@PostMapping("/save-request")
    public ResponseEntity<String> saveScheduleRequest(@RequestBody ScheduleRequest request) {
        try {
        	adminCalendarService.saveScheduleRequestAndSendEmail(request);
            return ResponseEntity.ok("Schedule request saved successfully");
        } catch (Exception e) {
            logger.error("Failed to save schedule request: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Failed to save schedule request: " + e.getMessage());
        }
    }
}
