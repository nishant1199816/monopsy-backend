package com.monopsy.app.dto;

public class WorkerProfileRequest {
    private String fullName, skillCategory, location, bio, phone, availability;
    private Integer experienceYears;
    private Double dailyRate, hourlyRate;

    public String getFullName()         { return fullName; }
    public String getSkillCategory()    { return skillCategory; }
    public String getLocation()         { return location; }
    public String getBio()              { return bio; }
    public String getPhone()            { return phone; }
    public String getAvailability()     { return availability; }
    public Integer getExperienceYears() { return experienceYears; }
    public Double getDailyRate()        { return dailyRate; }
    public Double getHourlyRate()       { return hourlyRate; }
    public void setFullName(String v)         { this.fullName = v; }
    public void setSkillCategory(String v)    { this.skillCategory = v; }
    public void setLocation(String v)         { this.location = v; }
    public void setBio(String v)              { this.bio = v; }
    public void setPhone(String v)            { this.phone = v; }
    public void setAvailability(String v)     { this.availability = v; }
    public void setExperienceYears(Integer v) { this.experienceYears = v; }
    public void setDailyRate(Double v)        { this.dailyRate = v; }
    public void setHourlyRate(Double v)       { this.hourlyRate = v; }
}
