package com.campus1.admin.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campus1.admin.entity.InterviewSchedule;

@Repository
public interface AdminInterviewScheduleRepository extends JpaRepository<InterviewSchedule, Long> {

    // Find an interview schedule by its state
    Optional<InterviewSchedule> findByState(String state);

    // Find an interview schedule by candidate email
    Optional<InterviewSchedule> findByCandidateEmail(String email);

}
