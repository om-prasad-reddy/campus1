package com.campus1.user.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    private String lastName;
    private String email;
    private String countryCode;
    
    @Column(name="phone")
    private String mobile;
    private String password;
    
    // Profile fields
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private String gender;
    private String address;
    private String fatherName;
    private String motherName;
    private String linkedinUrl;
    private String githubUrl;
    private String portfolioUrl;
    private String objective;
    
    @Lob
    @Column(name = "profile_photo", columnDefinition = "LONGBLOB")
    private byte[] profilePhoto;
    
    @Lob
    @Column(name = "signature", columnDefinition = "LONGBLOB")
    private byte[] signature;
    
    
    
    
    
    
    // Collections for profile sections
    @ElementCollection
    @CollectionTable(name = "user_education", joinColumns = @JoinColumn(name = "user_id"))
    private List<Education> education = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "user_id"))
    private List<Skill> skills = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "user_experience", joinColumns = @JoinColumn(name = "user_id"))
    private List<Experience> experience = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "user_projects", joinColumns = @JoinColumn(name = "user_id"))
    private List<Project> projects = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "user_certifications", joinColumns = @JoinColumn(name = "user_id"))
    private List<Certification> certifications = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "user_achievements", joinColumns = @JoinColumn(name = "user_id"))
    private List<Achievement> achievements = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "user_languages", joinColumns = @JoinColumn(name = "user_id"))
    private List<Language> languages = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "user_hobbies", joinColumns = @JoinColumn(name = "user_id"))
    private List<Hobby> hobbies = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "user_references", joinColumns = @JoinColumn(name = "user_id"))
    private List<Reference> references = new ArrayList<>();
    
    // Embeddable classes for profile sections
    @Embeddable
    @Data
    public static class Education {
        @Column(name = "institution_name")
        @JsonProperty("institution_name")
        private String institutionName;
        
        @Column(name = "degree")
        @JsonProperty("degree")
        private String degree;
        
        @Column(name = "branch")
        @JsonProperty("branch")
        private String branch;
        
        @Column(name = "start_year")
        @JsonProperty("start_year")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Integer startYear;
        
        @Column(name = "end_year")
        @JsonProperty("end_year")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Integer endYear;
        
        @Column(name = "grade")
        @JsonProperty("grade")
        private String grade;
    }
    
    @Embeddable
    @Data
    public static class Skill {
        @Column(name = "skill_name")
        @JsonProperty("skill_name")
        private String skillName;
        
        @Column(name = "proficiency_level")
        @JsonProperty("proficiency_level")
        private String proficiencyLevel;
    }
    
    @Embeddable
    @Data
    public static class Experience {
        @Column(name = "company_name")
        @JsonProperty("company_name")
        private String companyName;
        
        @Column(name = "role")
        @JsonProperty("role")
        private String role;
        
        @Column(name = "start_date")
        @JsonProperty("start_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date startDate;
        
        @Column(name = "end_date")
        @JsonProperty("end_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date endDate;
        
        @Column(name = "is_current")
        @JsonProperty("is_current")
        private Boolean isCurrent;
        
        @Column(name = "description")
        @JsonProperty("description")
        private String description;
    }
    
    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Project {
        @Column(name = "project_title")
        @JsonProperty("project_title")
        private String projectTitle;
        
        @Column(name = "technologies_used")
        @JsonProperty("technologies_used")
        private String technologiesUsed;
        
        @Column(name = "description")
        @JsonProperty("description")
        private String description;
        
        @Column(name = "project_link")
        @JsonProperty("project_link")
        private String projectLink;
    }
    
    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Certification {
        @Column(name = "certificate_name")
        @JsonProperty("certificate_name")
        private String certificateName;
        
        @Column(name = "issuing_organization")
        @JsonProperty("issuing_organization")
        private String issuingOrganization;
        
        @Column(name = "issue_date")
        @JsonProperty("issue_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date issueDate;
        
        @Column(name = "certificate_link")
        @JsonProperty("certificate_link")
        private String certificateLink;
        
        @Column(name = "certificate_file_name")
        @JsonProperty("certificate_file_name")
        private String certificateFileName;
        
        @Lob
        @Column(name = "certificate_file", columnDefinition = "LONGBLOB")
        @JsonProperty("certificate_file")
        private byte[] certificateFile;
    }
    
    @Embeddable
    @Data
    public static class Achievement {
        @Column(name = "title")
        @JsonProperty("title")
        private String title;
        
        @Column(name = "date")
        @JsonProperty("date")
        private Date date;
        
        @Column(name = "description")
        @JsonProperty("description")
        private String description;
    }
    
    @Embeddable
    @Data
    public static class Language {
        @Column(name = "language_name")
        @JsonProperty("language_name")
        private String languageName;
        
        @Column(name = "proficiency_level")
        @JsonProperty("proficiency_level")
        private String proficiencyLevel;
    }
    
    @Embeddable
    @Data
    public static class Hobby {
        @Column(name = "hobby_name")
        @JsonProperty("hobby_name")
        private String hobbyName;
    }
    
    @Embeddable
    @Data
    public static class Reference {
        @Column(name = "reference_name")
        @JsonProperty("reference_name")
        private String referenceName;
        
        @Column(name = "relation")
        @JsonProperty("relation")
        private String relation;
        
        @Column(name = "contact_info")
        @JsonProperty("contact_info")
        private String contactInfo;
    }
}