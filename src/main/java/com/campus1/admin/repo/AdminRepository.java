package com.campus1.admin.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campus1.admin.entity.Profile_Admin;

@Repository
public interface AdminRepository extends JpaRepository<Profile_Admin, Long> {
    Optional<Profile_Admin> findFirstByOrderByIdAsc();

    Profile_Admin findByEmail(String adminEmail);
}