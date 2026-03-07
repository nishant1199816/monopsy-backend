package com.monopsy.app.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "worker_profile")
public class WorkerProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private AppUser user;

    private String fullName;
    private String skillCategory;
    private Integer experienceYears;
    private Double dailyRate;
    private Double hourlyRate;
    private String location;
    private String phone;
    private String availability;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String photoUrl;
    private Double averageRating = 0.0;
    private Integer totalRatings = 0;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public WorkerProfile() {}

    public Long getId()                  { return id; }
    public AppUser getUser()             { return user; }
    public String getFullName()          { return fullName; }
    public String getSkillCategory()     { return skillCategory; }
    public Integer getExperienceYears()  { return experienceYears; }
    public Double getDailyRate()         { return dailyRate; }
    public Double getHourlyRate()        { return hourlyRate; }
    public String getLocation()          { return location; }
    public String getPhone()             { return phone; }
    public String getAvailability()      { return availability; }
    public String getBio()               { return bio; }
    public String getPhotoUrl()          { return photoUrl; }
    public Double getAverageRating()     { return averageRating; }
    public Integer getTotalRatings()     { return totalRatings; }
    public Instant getCreatedAt()        { return createdAt; }

    public void setUser(AppUser user)              { this.user = user; }
    public void setFullName(String v)              { this.fullName = v; }
    public void setSkillCategory(String v)         { this.skillCategory = v; }
    public void setExperienceYears(Integer v)      { this.experienceYears = v; }
    public void setDailyRate(Double v)             { this.dailyRate = v; }
    public void setHourlyRate(Double v)            { this.hourlyRate = v; }
    public void setLocation(String v)              { this.location = v; }
    public void setPhone(String v)                 { this.phone = v; }
    public void setAvailability(String v)          { this.availability = v; }
    public void setBio(String v)                   { this.bio = v; }
    public void setPhotoUrl(String v)              { this.photoUrl = v; }
    public void setAverageRating(Double v)         { this.averageRating = v; }
    public void setTotalRatings(Integer v)         { this.totalRatings = v; }
}
