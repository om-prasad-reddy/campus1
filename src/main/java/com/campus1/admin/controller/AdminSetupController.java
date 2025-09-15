package com.campus1.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class AdminSetupController {
    
    @GetMapping("/setup/google-calendar")
    public String googleCalendarSetup() {
        return "google-calendar-setup";
    }
}
