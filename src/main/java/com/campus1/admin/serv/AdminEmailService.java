package com.campus1.admin.serv;





import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.campus1.admin.dto.ScheduleRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class AdminEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendInterviewEmail(ScheduleRequest request, String confirmationLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(request.getCandidateEmail());
        helper.setSubject("Interview Scheduled: " + request.getTitle());

        // Generate Google Calendar add link
        String googleCalendarLink = generateGoogleCalendarLink(request);

        // Email body
        String body = "<html><body>" +
                "<h2>Interview Scheduled</h2>" +
                "<p><strong>Title:</strong> " + request.getTitle() + "</p>" +
                "<p><strong>Date:</strong> " + request.getInterviewDate() + "</p>" +
                "<p><strong>Time:</strong> " + request.getStartTime() + " - " + request.getEndTime() + "</p>" +
                "<p><strong>Description:</strong> " + (request.getDescription() != null ? request.getDescription() : "") + "</p>" +
                "<p><strong>Location:</strong> " + (request.getLocation() != null ? request.getLocation() : "Online") + "</p>" +
                "<p>Click the link below to add this event to your Google Calendar:</p>" +
                "<a href=\"" + googleCalendarLink + "\">Add to Google Calendar</a>" +
                "</body></html>";

        helper.setText(body, true); // true for HTML

        mailSender.send(message);
    }

    private String generateGoogleCalendarLink(ScheduleRequest request) {
        // Parse start and end datetimes (assuming UTC for simplicity; adjust timezone if needed)
        LocalDateTime startDateTime = LocalDateTime.parse(request.getInterviewDate() + "T" + request.getStartTime());
        LocalDateTime endDateTime = LocalDateTime.parse(request.getInterviewDate() + "T" + request.getEndTime() );

        // Format to YYYYMMDDTHHMMSSZ (UTC)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'").withZone(ZoneOffset.UTC);
        String startFormatted = startDateTime.atOffset(ZoneOffset.UTC).format(formatter);
        String endFormatted = endDateTime.atOffset(ZoneOffset.UTC).format(formatter);

        // Build URL
        return UriComponentsBuilder.fromUriString("https://calendar.google.com/calendar/render")
                .queryParam("action", "TEMPLATE")
                .queryParam("text", request.getTitle())
                .queryParam("dates", startFormatted + "/" + endFormatted)
                .queryParam("details", request.getDescription() != null ? request.getDescription() : "")
                .queryParam("location", request.getLocation() != null ? request.getLocation() : "")
                .queryParam("sf", "true")
                .queryParam("output", "xml")
                .build()
                .encode()
                .toUriString();
    }

	

}