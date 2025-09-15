package com.campus1.manager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campus1.manager.model.ManagerAdmin;

@Repository
public interface ManagerAdminRepository extends JpaRepository<ManagerAdmin,Long> {
	
	List<ManagerAdmin> findByManagerId(Long managerId);
	
	Optional<ManagerAdmin> findByEmail(String email);
	}
