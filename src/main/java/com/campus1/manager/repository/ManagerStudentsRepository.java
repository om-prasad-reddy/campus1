package com.campus1.manager.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.campus1.manager.model.ManagerStudents;

@Repository
public interface ManagerStudentsRepository extends JpaRepository<ManagerStudents,Long> {
	
	List<ManagerStudents> findByAdminId(Long adminId);
	
	
	List<ManagerStudents> findByManagerIdAndAdminIsNotNull(Long managerId);
	
	@Query("SELECT u FROM ManagerStudents u WHERE u.manager.id = :managerId AND u.admin IS NOT NULL AND (u.finalStatus Not IN ('Recruited','Rejected') OR u.finalStatus IS null)")
    List<ManagerStudents> findUnClosedTasks(@Param("managerId") Long managerId);

    // Closed tasks (Recruited or Rejected)
    @Query("SELECT u FROM ManagerStudents u WHERE u.manager.id = :managerId AND u.admin IS NOT NULL AND u.finalStatus IN ('Recruited','Rejected')")
    List<ManagerStudents> findClosedTasks(@Param("managerId") Long managerId);
    
    
    // Count all recruits for an admin (ignoring date)
    @Query("SELECT COUNT(u) FROM ManagerStudents u WHERE u.finalStatus = 'Recruited' AND u.admin.id = :adminId")
    long countRecruitsByAdmin(@Param("adminId") Long adminId);

    // Count recruits for an admin on a specific date
    @Query("SELECT COUNT(u) FROM ManagerStudents u WHERE u.finalStatus = 'Recruited' AND u.admin.id = :adminId AND u.finalStatusDate = :date")
    long countRecruitsByAdminAndDate(@Param("adminId") Long adminId, @Param("date") LocalDate date);
    
    
    
    
    @Query("SELECT u FROM ManagerStudents u WHERE u.admin.id = :adminId AND (u.finalStatus IS NULL OR u.finalStatus NOT IN ('Recruited','Rejected'))")
    List<ManagerStudents> findUnClosedTasksByAdminId(@Param("adminId") Long adminId);
    
 // Assigned users with date filter
    @Query("SELECT u FROM ManagerStudents u WHERE u.admin.id = :adminId AND u.assignDate = :date AND (u.finalStatus IS NULL OR u.finalStatus NOT IN ('Recruited','Rejected'))")
    List<ManagerStudents> findByAdminIdAndassignDate(@Param("adminId") Long adminId, @Param("date") LocalDate date);
    
    //Only recruited/rejected
    @Query("SELECT u FROM ManagerStudents u WHERE u.admin.id = :adminId AND u.finalStatus IN ('Recruited','Rejected')")
    List<ManagerStudents> findClosedTasksByAdminId(@Param("adminId") Long adminId);
    
	// Only recruited/rejected users by date
    List<ManagerStudents> findByAdminIdAndFinalStatusInAndFinalStatusDate(Long adminId,List<String> statuses,LocalDate finalStatusDate);
    
    
    
    
    
    
    
    @Query("SELECT DISTINCT u.callStatus FROM ManagerStudents u WHERE u.callStatus IS NOT NULL")
    List<String> findDistinctCallStatus();

    @Query("SELECT DISTINCT u.answeredCall FROM ManagerStudents u WHERE u.answeredCall IS NOT NULL")
    List<String> findDistinctAnsweredCall();

    @Query("SELECT DISTINCT u.lookingForJob FROM ManagerStudents u WHERE u.lookingForJob IS NOT NULL")
    List<String> findDistinctLookingForJob();

    @Query("SELECT DISTINCT u.eligibleForClient FROM ManagerStudents u WHERE u.eligibleForClient IS NOT NULL")
    List<String> findDistinctEligibleForClient();

    @Query("SELECT DISTINCT u.telephoneInterview FROM ManagerStudents u WHERE u.telephoneInterview IS NOT NULL")
    List<String> findDistinctTelephoneInterview();

    @Query("SELECT DISTINCT u.hrInterview FROM ManagerStudents u WHERE u.hrInterview IS NOT NULL")
    List<String> findDistinctHrInterview();

    @Query("SELECT DISTINCT u.operationRound FROM ManagerStudents u WHERE u.operationRound IS NOT NULL")
    List<String> findDistinctOperationRound();

    @Query("SELECT DISTINCT u.versantVoice FROM ManagerStudents u WHERE u.versantVoice IS NOT NULL")
    List<String> findDistinctVersantVoice();

    @Query("SELECT DISTINCT u.documentation FROM ManagerStudents u WHERE u.documentation IS NOT NULL")
    List<String> findDistinctDocumentation();

    @Query("SELECT DISTINCT u.offerLetter FROM ManagerStudents u WHERE u.offerLetter IS NOT NULL")
    List<String> findDistinctOfferLetter();

    @Query("SELECT DISTINCT u.joining FROM ManagerStudents u WHERE u.joining IS NOT NULL")
    List<String> findDistinctJoining();

    @Query("SELECT DISTINCT u.finalStatus FROM ManagerStudents u WHERE u.finalStatus IS NOT NULL")
    List<String> findDistinctFinalStatus();
}
