package com.campus1.admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "interview")
public class InterviewSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String candidateEmail;

    private String title;
    private String description;
    private String location;

    @Column(nullable = false)
    private String interviewDate;   // yyyy-MM-dd (better: LocalDate)

    @Column(nullable = false)
    private String startTime;       // better: LocalTime

//    @Column(nullable = false)
//    private String endTime;         // better: LocalTime

    private String state;

    // ✅ Relationship with Student_model
//    @OneToOne
//    @JoinColumn(name = "user_id", nullable = false)  // creates user_id column
//    private Student_model student;  // Renamed field for clarity
}
