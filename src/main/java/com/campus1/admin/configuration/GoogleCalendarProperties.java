//package com.example.student.configuration;
//
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.calendar.Calendar;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//
//@Configuration
//public class GoogleCalendarProperties {
//
//    private static final String APPLICATION_NAME = "Student Interview Scheduler";
//    private static final String SERVICE_ACCOUNT_KEY_PATH = "src/main/resources/your-service-account.json";
//
//    @Bean
//    public Calendar googleCalendarService() throws GeneralSecurityException, IOException {
//        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
//                .createScoped(Collections.singleton("https://www.googleapis.com/auth/calendar"));
//
//        return new Calendar.Builder(
//                GoogleNetHttpTransport.newTrustedTransport(),
//                JacksonFactory.getDefaultInstance(),
//                credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//    }



