package com.campus1.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {
	
	    private String candidateEmail;
	    private String interviewDate;
	    private String startTime;
	    private String endTime;
	    private String title;
	    private String description;
	    private String location;
	
}
