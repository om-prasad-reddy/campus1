// AdminTicketDto.java
package com.campus1.admin.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminTicketDto {
    private Long id;
    private String subject;
    private String category;
    private String priority;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private String adminEmail;
}