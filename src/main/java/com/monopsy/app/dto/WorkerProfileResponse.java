package com.monopsy.app.dto;
import com.monopsy.app.model.WorkerProfile;
import java.time.Instant;

public class WorkerProfileResponse {
    private Long id, userId;
    private String fullName, skillCategory, location, bio, phone, availability, photoUrl;
    private Integer experienceYears, totalRatings;
    private Double dailyRate, hourlyRate, averageRating;
    private Instant createdAt;

    public static WorkerProfileResponse from(WorkerProfile p) {
        WorkerProfileResponse r = new WorkerProfileResponse();
        r.id=p.getId();
        r.userId=p.getUser()!=null?p.getUser().getId():null;
        r.fullName=p.getFullName(); r.skillCategory=p.getSkillCategory();
        r.location=p.getLocation(); r.bio=p.getBio();
        r.phone=p.getPhone(); r.availability=p.getAvailability();
        r.photoUrl=p.getPhotoUrl(); r.experienceYears=p.getExperienceYears();
        r.dailyRate=p.getDailyRate(); r.hourlyRate=p.getHourlyRate();
        r.averageRating=p.getAverageRating(); r.totalRatings=p.getTotalRatings();
        r.createdAt=p.getCreatedAt();
        return r;
    }
    public Long getId()                { return id; }
    public Long getUserId()            { return userId; }
    public String getFullName()        { return fullName; }
    public String getSkillCategory()   { return skillCategory; }
    public String getLocation()        { return location; }
    public String getBio()             { return bio; }
    public String getPhone()           { return phone; }
    public String getAvailability()    { return availability; }
    public String getPhotoUrl()        { return photoUrl; }
    public Integer getExperienceYears(){ return experienceYears; }
    public Integer getTotalRatings()   { return totalRatings; }
    public Double getDailyRate()       { return dailyRate; }
    public Double getHourlyRate()      { return hourlyRate; }
    public Double getAverageRating()   { return averageRating; }
    public Instant getCreatedAt()      { return createdAt; }
}
