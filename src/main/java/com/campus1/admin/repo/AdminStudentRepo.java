package com.campus1.admin.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.campus1.admin.entity.Admin_Tracking_System;

@Repository
public interface AdminStudentRepo extends JpaRepository<Admin_Tracking_System, Long> {
    
    @Query("SELECT s FROM Admin_Tracking_System s WHERE s.profile_Admin.id = :adminId")
    List<Admin_Tracking_System> findByProfileAdminId(@Param("adminId") Long adminId);
    
    Optional<Admin_Tracking_System> findById(Long id);
}