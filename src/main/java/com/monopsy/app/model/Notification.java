package com.monopsy.app.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "notification")
public class Notification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long recipientUserId;

    private String type;

    @Column(columnDefinition = "TEXT")
    private String message;

    private Boolean read = false;
    private Long jobId;
    private Long actorUserId;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    private Instant readAt;

    public Notification() {}

    public Long getId()               { return id; }
    public Long getRecipientUserId()  { return recipientUserId; }
    public String getType()           { return type; }
    public String getMessage()        { return message; }
    public Boolean getRead()          { return read; }
    public Long getJobId()            { return jobId; }
    public Long getActorUserId()      { return actorUserId; }
    public Instant getCreatedAt()     { return createdAt; }
    public Instant getReadAt()        { return readAt; }

    public void setRecipientUserId(Long v) { this.recipientUserId = v; }
    public void setType(String v)          { this.type = v; }
    public void setMessage(String v)       { this.message = v; }
    public void setRead(Boolean v)         { this.read = v; }
    public void setJobId(Long v)           { this.jobId = v; }
    public void setActorUserId(Long v)     { this.actorUserId = v; }
    public void setReadAt(Instant v)       { this.readAt = v; }
}
