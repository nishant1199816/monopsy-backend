package com.monopsy.app.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "job_application",
       uniqueConstraints = @UniqueConstraint(columnNames = {"job_id", "worker_id"}))
public class JobApplication {

    public enum Status { PENDING, ACCEPTED, REJECTED }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "worker_id", nullable = false)
    private Long workerId;

    private String message;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public JobApplication() {}

    public Long getId()        { return id; }
    public Long getJobId()     { return jobId; }
    public Long getWorkerId()  { return workerId; }
    public String getMessage() { return message; }
    public Status getStatus()  { return status; }
    public Instant getCreatedAt() { return createdAt; }

    public void setJobId(Long v)     { this.jobId = v; }
    public void setWorkerId(Long v)  { this.workerId = v; }
    public void setMessage(String v) { this.message = v; }
    public void setStatus(Status v)  { this.status = v; }
}
