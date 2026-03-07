package com.monopsy.app.dto;

public class JobApplicationResponse {
    private Long applicationId, workerId;
    private String status, message, workerName, skill, location;
    private Integer experienceYears;
    private Double dailyRate;

    public Long getApplicationId()     { return applicationId; }
    public Long getWorkerId()          { return workerId; }
    public String getStatus()          { return status; }
    public String getMessage()         { return message; }
    public String getWorkerName()      { return workerName; }
    public String getSkill()           { return skill; }
    public String getLocation()        { return location; }
    public Integer getExperienceYears(){ return experienceYears; }
    public Double getDailyRate()       { return dailyRate; }
    public void setApplicationId(Long v)      { this.applicationId = v; }
    public void setWorkerId(Long v)           { this.workerId = v; }
    public void setStatus(String v)           { this.status = v; }
    public void setMessage(String v)          { this.message = v; }
    public void setWorkerName(String v)       { this.workerName = v; }
    public void setSkill(String v)            { this.skill = v; }
    public void setLocation(String v)         { this.location = v; }
    public void setExperienceYears(Integer v) { this.experienceYears = v; }
    public void setDailyRate(Double v)        { this.dailyRate = v; }
}
