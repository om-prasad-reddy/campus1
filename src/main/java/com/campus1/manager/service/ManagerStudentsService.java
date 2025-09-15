package com.campus1.manager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.campus1.manager.dto.ManagerStudentModelDto;
import com.campus1.manager.dto.ManagerUserPieModelDto;
import com.campus1.manager.exception.ManagerUsersNotFound;
import com.campus1.manager.model.ManagerStudents;
import com.campus1.manager.repository.ManagerStudentsRepository;

@Service
public class ManagerStudentsService {
	
	private final ManagerStudentsRepository stuRepo;
	
	public ManagerStudentsService(ManagerStudentsRepository stuRepo) {
		this.stuRepo=stuRepo;
	}
	
	public List<ManagerStudents> getTaskAssignedToManagerToAdmin(Long managerid){
    	return stuRepo.findByManagerIdAndAdminIsNotNull(managerid);
    }
	
	
	public List<ManagerStudents> getUnClosedTasks(Long managerId) {
        List<ManagerStudents> abc = stuRepo.findUnClosedTasks(managerId);
        return abc;
    }

    public List<ManagerStudents> getClosedTasks(Long managerId) {
        return stuRepo.findClosedTasks(managerId);
    }
    
    public ManagerStudentModelDto getUserById(Long userId) {
		ManagerStudents user = stuRepo.findById(userId)
	            .orElseThrow(() -> new ManagerUsersNotFound("ManagerUsers Not Found"));
	    
		return new ManagerStudentModelDto(user.getId(),user.getName(),user.getEmail(),user.getQualification(),
	            user.getPhone(),user.getCallStatus(),user.getAnsweredCall(),user.getLookingForJob(),
	            user.getEligibleForClient(),user.getTelephoneInterview(),user.getHrInterview(),
	            user.getOperationRound(),user.getVersantVoice(),user.getDocumentation(),
	            user.getOfferLetter(),user.getJoining(),user.getFinalStatus(),user.getFinalStatusDate()
	    );
	}
    
    public List<ManagerUserPieModelDto> getUsersByAdminId(Long adminId, LocalDate date, String tab) {
        List<ManagerStudents> student = new ArrayList<>();

        if ("assigned".equalsIgnoreCase(tab)) {
            if (date != null) {
                // Assigned on specific date
            	student = stuRepo.findByAdminIdAndassignDate(adminId, date);
            } else {
                // All assigned but not closed (under that admin)
            	student = stuRepo.findUnClosedTasksByAdminId(adminId); // Updated
            }
        } else if ("recruited".equalsIgnoreCase(tab)) {
            if (date != null) {
            	student = stuRepo.findByAdminIdAndFinalStatusInAndFinalStatusDate(
                        adminId,
                        List.of("Recruited"),
                        date
                );
            } else {
            	student = stuRepo.findClosedTasksByAdminId(adminId).stream() // Updated
                        .filter(u -> "Recruited".equalsIgnoreCase(u.getFinalStatus()))
                        .toList();
            }
        } else if ("fileClosed".equalsIgnoreCase(tab)) {
            if (date != null) {
            	student = stuRepo.findByAdminIdAndFinalStatusInAndFinalStatusDate(
                        adminId,
                        List.of("Recruited", "Rejected"),
                        date
                );
            } else {
            	student = stuRepo.findClosedTasksByAdminId(adminId); // Updated
            }
        } else {
            // Fallback: return all users under admin
        	student = stuRepo.findByAdminId(adminId);
        }
        return student.stream().map(ManagerUserPieModelDto::new).toList();
    }
}
