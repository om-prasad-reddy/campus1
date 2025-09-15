package com.campus1.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.campus1.user.entity.User_Otp;

@Repository
public interface User_OtpRepository extends JpaRepository<User_Otp, Long> {
    Optional<User_Otp> findByEmail(String email);
    
    @Modifying
    @Transactional
    @Query("UPDATE User_Otp o SET o.verified = true WHERE o.email = :email")
    void markAsVerified(@Param("email") String email);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM User_Otp o WHERE o.email = :email")
    void deleteByEmail(@Param("email") String email);
}