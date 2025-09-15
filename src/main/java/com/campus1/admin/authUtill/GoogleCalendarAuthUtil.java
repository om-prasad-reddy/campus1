package com.campus1.admin.authUtill;
//
//
//
//
//import com.example.student.configuration.GoogleCalendarProperties;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.services.calendar.CalendarScopes;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//
//@Component
//public class GoogleCalendarAuthUtil {
//
//    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//    
//    @Autowired
//    private GoogleCalendarProperties calendarProperties;
//    
//    public GoogleCredential getCredential() throws IOException, GeneralSecurityException {
//        return new GoogleCredential.Builder()
//                .setTransport(GoogleNetHttpTransport.newTrustedTransport())
//                .setJsonFactory(JSON_FACTORY)
//                .setClientSecrets(calendarProperties.getClientId(), calendarProperties.getClientSecret())
//                .build()
//                .setAccessToken(calendarProperties.getAccessToken())
//                .setRefreshToken(calendarProperties.getRefreshToken());
//    }
//}