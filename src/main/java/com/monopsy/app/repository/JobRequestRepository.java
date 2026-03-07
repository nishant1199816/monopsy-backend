package com.monopsy.app.repository;

import com.monopsy.app.model.JobRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRequestRepository extends JpaRepository<JobRequest, Long> {
    List<JobRequest> findByEmployerId(Long employerId);
    List<JobRequest> findByAssignedWorkerId(Long workerId);
    List<JobRequest> findByAssignedWorkerIdAndStatus(Long workerId, JobRequest.Status status);
    List<JobRequest> findByStatus(JobRequest.Status status);
}
