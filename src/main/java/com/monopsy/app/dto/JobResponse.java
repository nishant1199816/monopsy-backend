package com.monopsy.app.dto;
import com.monopsy.app.model.JobRequest;
import java.time.Instant;

public class JobResponse {
    private Long id, employerId, assignedWorkerId;
    private String title, description, location, category, jobType, salary, contactPhone, status;
    private Double latitude, longitude;
    private Instant createdAt;

    public static JobResponse from(JobRequest j) {
        JobResponse r = new JobResponse();
        r.id=j.getId(); r.employerId=j.getEmployerId();
        r.title=j.getTitle(); r.description=j.getDescription();
        r.location=j.getLocation(); r.category=j.getCategory();
        r.jobType=j.getJobType(); r.salary=j.getSalary();
        r.contactPhone=j.getContactPhone();
        r.status=j.getStatus().name();
        r.assignedWorkerId=j.getAssignedWorkerId();
        r.latitude=j.getLatitude(); r.longitude=j.getLongitude();
        r.createdAt=j.getCreatedAt();
        return r;
    }
    public Long getId()               { return id; }
    public Long getEmployerId()       { return employerId; }
    public Long getAssignedWorkerId() { return assignedWorkerId; }
    public String getTitle()          { return title; }
    public String getDescription()    { return description; }
    public String getLocation()       { return location; }
    public String getCategory()       { return category; }
    public String getJobType()        { return jobType; }
    public String getSalary()         { return salary; }
    public String getContactPhone()   { return contactPhone; }
    public String getStatus()         { return status; }
    public Double getLatitude()       { return latitude; }
    public Double getLongitude()      { return longitude; }
    public Instant getCreatedAt()     { return createdAt; }
}
