package com.monopsy.app.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "job_request")
public class JobRequest {

    public enum Status { OPEN, ASSIGNED, COMPLETED }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long employerId;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private String location;
    private String category;
    private String jobType;
    private String salary;
    private String contactPhone;
    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.OPEN;

    private Long assignedWorkerId;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public JobRequest() {}

    public Long getId()                  { return id; }
    public Long getEmployerId()          { return employerId; }
    public String getTitle()             { return title; }
    public String getDescription()       { return description; }
    public String getLocation()          { return location; }
    public String getCategory()          { return category; }
    public String getJobType()           { return jobType; }
    public String getSalary()            { return salary; }
    public String getContactPhone()      { return contactPhone; }
    public Double getLatitude()          { return latitude; }
    public Double getLongitude()         { return longitude; }
    public Status getStatus()            { return status; }
    public Long getAssignedWorkerId()    { return assignedWorkerId; }
    public Instant getCreatedAt()        { return createdAt; }

    public void setEmployerId(Long v)        { this.employerId = v; }
    public void setTitle(String v)           { this.title = v; }
    public void setDescription(String v)     { this.description = v; }
    public void setLocation(String v)        { this.location = v; }
    public void setCategory(String v)        { this.category = v; }
    public void setJobType(String v)         { this.jobType = v; }
    public void setSalary(String v)          { this.salary = v; }
    public void setContactPhone(String v)    { this.contactPhone = v; }
    public void setLatitude(Double v)        { this.latitude = v; }
    public void setLongitude(Double v)       { this.longitude = v; }
    public void setStatus(Status v)          { this.status = v; }
    public void setAssignedWorkerId(Long v)  { this.assignedWorkerId = v; }
}
