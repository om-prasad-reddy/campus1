package com.campus1.admin.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "trackingSystems")
@Table(name = "admins") // Changed to lowercase to match ManagerAdmin
public class Profile_Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String name; // Changed from fullName to name

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String pwd; // Changed from password to pwd

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    @Transient
    private Long adminId;

    @Lob
    @Column(name = "profile_picture", columnDefinition = "MEDIUMBLOB")
    private byte[] profilePicture;

    @Column(name = "profile_picture_type")
    private String profilePictureType;

    @OneToMany(mappedBy = "profile_Admin", cascade = CascadeType.ALL)
    private List<Admin_Tracking_System> trackingSystems;

    // Override Lombok getters and setters to use original method names
    public String getFullName() {
        return name;
    }

    public void setFullName(String fullName) {
        this.name = fullName;
    }

    public String getPassword() {
        return pwd;
    }

    public void setPassword(String password) {
        this.pwd = password;
    }

    // Keep Lombok-generated getters and setters for other fields
}