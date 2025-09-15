package com.campus1.admin.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "profile_Admin")
@Table(name = "students")
public class Admin_Tracking_System {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String department;
    private int age;
    private String phone;
    private String callStatus;
    private String answeredCall;
    private String lookingForJob;
    private String eligibleForClient;
    private String telephoneInterview;
    private String hrInterview;
    private String operationRound;
    private String versantVoice;
    private String documentation;
    private String offerLetter;
    private String joining;
    private LocalDate date;
    private LocalTime time;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean closed = false;
    // New fields for final status
    private String finalStatus;
    private LocalDate finalStatusDate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Admin_id")
    private Profile_Admin profile_Admin;
    // Added qualification field
    private String qualification;
}