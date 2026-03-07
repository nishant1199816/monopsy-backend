package com.monopsy.app.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "rating")
public class Rating {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long jobId;

    @Column(nullable = false)
    private Long raterUserId;

    @Column(nullable = false)
    private Long targetUserId;

    @Column(nullable = false)
    private Integer score;

    private String feedback;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public Rating() {}

    public Long getId()           { return id; }
    public Long getJobId()        { return jobId; }
    public Long getRaterUserId()  { return raterUserId; }
    public Long getTargetUserId() { return targetUserId; }
    public Integer getScore()     { return score; }
    public String getFeedback()   { return feedback; }
    public Instant getCreatedAt() { return createdAt; }

    public void setJobId(Long v)         { this.jobId = v; }
    public void setRaterUserId(Long v)   { this.raterUserId = v; }
    public void setTargetUserId(Long v)  { this.targetUserId = v; }
    public void setScore(Integer v)      { this.score = v; }
    public void setFeedback(String v)    { this.feedback = v; }
}
