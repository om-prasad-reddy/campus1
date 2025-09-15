package com.campus1.manager.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Students")
public class ManagerStudents {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  

    private String answeredCall;

    private String callStatus;

    private boolean closed;

    private String qualification;

    private String documentation;

    private String eligibleForClient;

    private String email;


    private String hrInterview;

    private String joining;

    private String lookingForJob;

    private String name;


    private String offerLetter;


    private String operationRound;

    private long phone;


    private String telephoneInterview;



    private String versantVoice;

    private LocalDate assignDate;


    private String finalStatus;
    
    private LocalDate finalStatusDate;

    // Relations
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private ManagerAdmin admin;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private ManagerManager manager;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private ManagerUsers user;

    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getAnsweredCall() {
		return answeredCall;
	}

	public void setAnsweredCall(String answeredCall) {
		this.answeredCall = answeredCall;
	}

	public String getCallStatus() {
		return callStatus;
	}

	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

	public String getEligibleForClient() {
		return eligibleForClient;
	}

	public void setEligibleForClient(String eligibleForClient) {
		this.eligibleForClient = eligibleForClient;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHrInterview() {
		return hrInterview;
	}

	public void setHrInterview(String hrInterview) {
		this.hrInterview = hrInterview;
	}

	public String getJoining() {
		return joining;
	}

	public void setJoining(String joining) {
		this.joining = joining;
	}

	public String getLookingForJob() {
		return lookingForJob;
	}

	public void setLookingForJob(String lookingForJob) {
		this.lookingForJob = lookingForJob;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOfferLetter() {
		return offerLetter;
	}

	public void setOfferLetter(String offerLetter) {
		this.offerLetter = offerLetter;
	}

	public String getOperationRound() {
		return operationRound;
	}

	public void setOperationRound(String operationRound) {
		this.operationRound = operationRound;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getTelephoneInterview() {
		return telephoneInterview;
	}

	public void setTelephoneInterview(String telephoneInterview) {
		this.telephoneInterview = telephoneInterview;
	}

	public String getVersantVoice() {
		return versantVoice;
	}

	public void setVersantVoice(String versantVoice) {
		this.versantVoice = versantVoice;
	}

	public ManagerAdmin getAdmin() {
		return admin;
	}

	public void setAdmin(ManagerAdmin admin) {
		this.admin = admin;
	}

	public ManagerManager getManager() {
		return manager;
	}

	public void setManager(ManagerManager manager) {
		this.manager = manager;
	}

	public LocalDate getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(LocalDate assignDate) {
		this.assignDate = assignDate;
	}

	public String getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}

	public LocalDate getFinalStatusDate() {
		return finalStatusDate;
	}

	public void setFinalStatusDate(LocalDate finalStatusDate) {
		this.finalStatusDate = finalStatusDate;
	}

	public ManagerUsers getUser() {
		return user;
	}

	public void setUser(ManagerUsers user) {
		this.user = user;
	}
	
	
   
}
