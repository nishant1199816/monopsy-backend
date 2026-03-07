package com.monopsy.app.service;

import com.monopsy.app.dto.RatingRequest;
import com.monopsy.app.model.*;
import com.monopsy.app.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository        ratingRepo;
    private final AppUserRepository       userRepo;
    private final JobRequestRepository    jobRepo;
    private final WorkerProfileRepository workerRepo;

    public RatingService(RatingRepository ratingRepo,
                         AppUserRepository userRepo,
                         JobRequestRepository jobRepo,
                         WorkerProfileRepository workerRepo) {
        this.ratingRepo  = ratingRepo;
        this.userRepo    = userRepo;
        this.jobRepo     = jobRepo;
        this.workerRepo  = workerRepo;
    }

    @Transactional
    public Rating rateWorker(Long jobId, RatingRequest req, String raterEmail) {
        AppUser rater = getUser(raterEmail);

        JobRequest job = jobRepo.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));
        if (job.getStatus() != JobRequest.Status.COMPLETED)
            throw new IllegalStateException("Can only rate completed jobs");
        if (!job.getEmployerId().equals(rater.getId()))
            throw new SecurityException("Only the employer can rate");
        if (ratingRepo.existsByJobIdAndRaterUserId(jobId, rater.getId()))
            throw new IllegalStateException("Already rated this job");

        // Worker's AppUser ID
        WorkerProfile wp = workerRepo.findById(job.getAssignedWorkerId())
                .orElseThrow(() -> new IllegalArgumentException("Worker profile not found"));
        Long targetUserId = wp.getUser().getId();

        Rating r = new Rating();
        r.setJobId(jobId);
        r.setRaterUserId(rater.getId());
        r.setTargetUserId(targetUserId);
        r.setScore(req.getScore());
        r.setFeedback(req.getFeedback());
        Rating saved = ratingRepo.save(r);

        // Update worker's cached average
        Double avg   = ratingRepo.findAverageScoreByTargetUserId(targetUserId);
        Long   count = ratingRepo.countByTargetUserId(targetUserId);
        wp.setAverageRating(avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0);
        wp.setTotalRatings(count.intValue());
        workerRepo.save(wp);

        return saved;
    }

    public List<Rating> getRatingsForWorker(Long userId) {
        return ratingRepo.findByTargetUserId(userId);
    }

    private AppUser getUser(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
