package com.campus1.admin.serv;

import java.io.StringReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import com.campus1.admin.dto.ScheduleRequest;
import com.campus1.admin.entity.InterviewSchedule;
import com.campus1.admin.repo.AdminInterviewScheduleRepository;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@Transactional
public class AdminCalendarService {
    private static final Logger logger = LoggerFactory.getLogger(AdminCalendarService.class);
    @Autowired
    private AdminInterviewScheduleRepository repository;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;
    
    private static final String APPLICATION_NAME = "Interview Scheduler";
    private static final DateTimeFormatter RFC3339_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    public String scheduleAndGenerateAuthLink(ScheduleRequest request) throws Exception {
        // Save to database
        InterviewSchedule schedule = new InterviewSchedule();
        schedule.setCandidateEmail(request.getCandidateEmail());
        schedule.setInterviewDate(request.getInterviewDate());
        schedule.setStartTime(request.getStartTime());
     //   schedule.setEndTime(request.getEndTime());
        schedule.setTitle(request.getTitle());
        schedule.setDescription(request.getDescription());
        schedule.setLocation(request.getLocation());
        schedule.setState(UUID.randomUUID().toString());  // important for OAuth flow
        repository.save(schedule);
        // Generate OAuth2 authorization URL
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                GsonFactory.getDefaultInstance(),
                new StringReader("{\"web\":{\"client_id\":\"" + clientId +
                        "\",\"client_secret\":\"" + clientSecret + "\"}}")
        );
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                clientSecrets,
                Collections.singleton(CalendarScopes.CALENDAR_EVENTS)
        ).setDataStoreFactory(new MemoryDataStoreFactory()).build();
        AuthorizationCodeRequestUrl authUrl = flow.newAuthorizationUrl()
                .setRedirectUri(redirectUri)
                .setState(schedule.getState());
        return authUrl.build();
    }
    public void createCalendarEvent(String code, String state) throws Exception {
        InterviewSchedule schedule = repository.findByState(state)
                .orElseThrow(() -> new IllegalStateException("Invalid or expired state parameter"));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                GsonFactory.getDefaultInstance(),
                new StringReader("{\"web\":{\"client_id\":\"" + clientId +
                        "\",\"client_secret\":\"" + clientSecret + "\"}}")
        );
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                clientSecrets,
                Collections.singleton(CalendarScopes.CALENDAR_EVENTS)
        ).setDataStoreFactory(new MemoryDataStoreFactory()).build();
        TokenResponse tokenResponse = flow.newTokenRequest(code)
                .setRedirectUri(redirectUri)
                .execute();
        Credential credential = flow.createAndStoreCredential(tokenResponse, schedule.getCandidateEmail());
        Calendar calendar = new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                credential
        ).setApplicationName(APPLICATION_NAME).build();
        // Parse date and time
        LocalDateTime startLocal = LocalDateTime.parse(
                schedule.getInterviewDate() + "T" + schedule.getStartTime() ,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//        LocalDateTime endLocal = LocalDateTime.parse(
//                schedule.getInterviewDate() + "T" + schedule.getEndTime() ,
//                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        ZoneId zone = ZoneId.of("Asia/Kolkata"); // adjust if needed
        Event event = new Event()
                .setSummary(schedule.getTitle())
                .setDescription(schedule.getDescription())
                .setLocation(schedule.getLocation());
        EventDateTime start = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(
                        startLocal.atZone(zone).format(RFC3339_FORMATTER)))
                .setTimeZone(zone.getId());
        event.setStart(start);
//        EventDateTime end = new EventDateTime()
//                .setDateTime(new com.google.api.client.util.DateTime(
//                        endLocal.atZone(zone).format(RFC3339_FORMATTER)))
//                .setTimeZone(zone.getId());
//        event.setEnd(end);
        EventAttendee[] attendees = new EventAttendee[]{
                new EventAttendee().setEmail(schedule.getCandidateEmail())
        };
        event.setAttendees(Arrays.asList(attendees));
        calendar.events().insert("primary", event).setSendUpdates("all").execute();
        logger.info("Event created for {}", schedule.getCandidateEmail());
        // Optional: mark schedule processed instead of delete
         repository.delete(schedule);
    }
    // ✅ FIXED: return ScheduleRequest, not InterviewSchedule
    public ScheduleRequest getScheduleRequestById(Long id) {
        InterviewSchedule schedule = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No schedule found with ID " + id));
        return ScheduleRequest.builder()
                .candidateEmail(schedule.getCandidateEmail())
                .interviewDate(schedule.getInterviewDate())
                .startTime(schedule.getStartTime())
            //    .endTime(schedule.getEndTime())
                .title(schedule.getTitle())
                .description(schedule.getDescription())
                .location(schedule.getLocation())
                .build();
    }
   
    public InterviewSchedule saveScheduleRequestAndSendEmail(ScheduleRequest dto) {
        // 1️⃣ Save to database
        InterviewSchedule entity = InterviewSchedule.builder()
                .candidateEmail(dto.getCandidateEmail())
                .interviewDate(dto.getInterviewDate())
                .startTime(dto.getStartTime())
            //    .endTime(dto.getEndTime())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .state("Scheduled")
                .build();
        InterviewSchedule saved = repository.save(entity);

        logger.info("Interview schedule saved for {}", saved.getCandidateEmail());
        // 2️⃣ Send email immediately
        try {
            sendInterviewEmail(dto);
            logger.info("Interview email sent to {}", saved.getCandidateEmail());
        } catch (MessagingException e) {
            logger.error("Failed to send interview email to {}: {}", saved.getCandidateEmail(), e.getMessage());
        }
        return saved;
    }
    /**
     * Send interview email with Google Calendar link
     */
    private void sendInterviewEmail(ScheduleRequest request) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo(request.getCandidateEmail());
        helper.setSubject("Interview Scheduled: " + request.getTitle());
        String googleCalendarLink = generateGoogleCalendarLink(request);
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
        helper.setText(body, true); // HTML
        mailSender.send(message);
    }
    
     
 // Generate Google Calendar Link
    private String generateGoogleCalendarLink(ScheduleRequest request) {
        // 1️⃣ Parse date and time from DB
        LocalDate date = LocalDate.parse(request.getInterviewDate());
        LocalTime start = LocalTime.parse(request.getStartTime());
        LocalTime end = LocalTime.parse(request.getEndTime());

        LocalDateTime startDateTime = LocalDateTime.of(date, start);
        LocalDateTime endDateTime = LocalDateTime.of(date, end);

        // 2️⃣ Set Kolkata timezone (UTC+5:30)
        ZoneId kolkataZone = ZoneId.of("Asia/Kolkata");

        // 3️⃣ Convert to UTC for Google Calendar
        DateTimeFormatter googleFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
                .withZone(ZoneOffset.UTC);

        String startFormatted = googleFormatter.format(startDateTime.atZone(kolkataZone));
        String endFormatted = googleFormatter.format(endDateTime.atZone(kolkataZone));

        // 4️⃣ Build the Google Calendar URL
        return UriComponentsBuilder.fromUriString("https://calendar.google.com/calendar/render")
                .queryParam("action", "TEMPLATE")
                .queryParam("text", encode(request.getTitle()))
                .queryParam("dates", startFormatted + "/" + endFormatted)
                .queryParam("details", request.getDescription() != null ? encode(request.getDescription()) : "")
                .queryParam("location", request.getLocation() != null ? encode(request.getLocation()) : "")
                .queryParam("sf", "true")
                .queryParam("output", "xml")
                .build()
                .toUriString();
    }

    


    // Clean malformed time strings like "2025-08-20T22:29:00:00"
    private String cleanDateTime(String dateTime) {
        long colonCount = dateTime.chars().filter(ch -> ch == ':').count();
        if (colonCount == 3) {
            return dateTime.substring(0, dateTime.lastIndexOf(':'));
        }
        return dateTime;
    }

    // Encode parameters safely
    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }



	
}   