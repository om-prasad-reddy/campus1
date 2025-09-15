package com.campus1.manager.dto;

import java.time.LocalDate;

public class ManagerStudentModelDto {
	private Long id;
    private String name;
    private String email;
    private String department;
    
    private long phone;
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
    private String finalStatus;
    private LocalDate recruitedDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getCallStatus() {
		return callStatus;
	}
	public void setCallStatus(String callStatus) {
		this.callStatus = callStatus;
	}
	public String getAnsweredCall() {
		return answeredCall;
	}
	public void setAnsweredCall(String answeredCall) {
		this.answeredCall = answeredCall;
	}
	public String getLookingForJob() {
		return lookingForJob;
	}
	public void setLookingForJob(String lookingForJob) {
		this.lookingForJob = lookingForJob;
	}
	public String getEligibleForClient() {
		return eligibleForClient;
	}
	public void setEligibleForClient(String eligibleForClient) {
		this.eligibleForClient = eligibleForClient;
	}
	public String getTelephoneInterview() {
		return telephoneInterview;
	}
	public void setTelephoneInterview(String telephoneInterview) {
		this.telephoneInterview = telephoneInterview;
	}
	public String getHrInterview() {
		return hrInterview;
	}
	public void setHrInterview(String hrInterview) {
		this.hrInterview = hrInterview;
	}
	public String getOperationRound() {
		return operationRound;
	}
	public void setOperationRound(String operationRound) {
		this.operationRound = operationRound;
	}
	public String getVersantVoice() {
		return versantVoice;
	}
	public void setVersantVoice(String versantVoice) {
		this.versantVoice = versantVoice;
	}
	public String getDocumentation() {
		return documentation;
	}
	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	public String getOfferLetter() {
		return offerLetter;
	}
	public void setOfferLetter(String offerLetter) {
		this.offerLetter = offerLetter;
	}
	public String getJoining() {
		return joining;
	}
	public void setJoining(String joining) {
		this.joining = joining;
	}
	public String getFinalStatus() {
		return finalStatus;
	}
	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}
	public LocalDate getRecruitedDate() {
		return recruitedDate;
	}
	public void setRecruitedDate(LocalDate recruitedDate) {
		this.recruitedDate = recruitedDate;
	}
	public ManagerStudentModelDto(Long id, String name, String email, String department, long phone, String callStatus,
			String answeredCall, String lookingForJob, String eligibleForClient, String telephoneInterview,
			String hrInterview, String operationRound, String versantVoice, String documentation, String offerLetter,
			String joining, String finalStatus, LocalDate recruitedDate) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.department = department;
		
		this.phone = phone;
		this.callStatus = callStatus;
		this.answeredCall = answeredCall;
		this.lookingForJob = lookingForJob;
		this.eligibleForClient = eligibleForClient;
		this.telephoneInterview = telephoneInterview;
		this.hrInterview = hrInterview;
		this.operationRound = operationRound;
		this.versantVoice = versantVoice;
		this.documentation = documentation;
		this.offerLetter = offerLetter;
		this.joining = joining;
		this.finalStatus = finalStatus;
		this.recruitedDate = recruitedDate;
	}
}
