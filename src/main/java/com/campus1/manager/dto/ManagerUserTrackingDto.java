package com.campus1.manager.dto;


import com.campus1.manager.model.ManagerStudents;

public class ManagerUserTrackingDto {
	private Long id;
    private String name;
    private String email;
    private String qualification;
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
    private String admin;
    
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
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
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
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	
//	public ManagerUserTrackingDto(ManagerUsers user) {
//        this.id = user.getId();
//        this.name = user.getName();
//        this.email = user.getEmail();
//        this.department = user.getDepartment();
//        this.phone = user.getPhone();
//        this.callStatus = user.getCallStatus();
//        this.answeredCall = user.getAnsweredCall();
//        this.lookingForJob = user.getLookingForJob();
//        this.eligibleForClient = user.getEligibleForClient();
//        this.telephoneInterview = user.getTelephoneInterview();
//        this.hrInterview = user.getHrInterview();
//        this.operationRound = user.getOperationRound();
//        this.versantVoice = user.getVersantVoice();
//        this.documentation = user.getDocumentation();
//        this.offerLetter = user.getOfferLetter();
//        this.joining = user.getJoining();
//        this.finalStatus = user.getFinalStatus();
//        this.admin = user.getAdmin() != null ? user.getAdmin().getName() : null; 
//    }
	
	public ManagerUserTrackingDto(ManagerStudents user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.qualification = user.getQualification();
        this.phone = user.getPhone();
        this.callStatus = user.getCallStatus();
        this.answeredCall = user.getAnsweredCall();
        this.lookingForJob = user.getLookingForJob();
        this.eligibleForClient = user.getEligibleForClient();
        this.telephoneInterview = user.getTelephoneInterview();
        this.hrInterview = user.getHrInterview();
        this.operationRound = user.getOperationRound();
        this.versantVoice = user.getVersantVoice();
        this.documentation = user.getDocumentation();
        this.offerLetter = user.getOfferLetter();
        this.joining = user.getJoining();
        this.finalStatus = user.getFinalStatus();
        this.admin = user.getAdmin() != null ? user.getAdmin().getName() : null; 
    }
}
