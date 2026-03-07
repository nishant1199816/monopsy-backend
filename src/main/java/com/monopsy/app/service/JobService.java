package com.monopsy.app.service;

import com.monopsy.app.dto.*;
import com.monopsy.app.model.*;
import com.monopsy.app.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRequestRepository     jobRepo;
    private final JobApplicationRepository appRepo;
    private final WorkerProfileRepository  workerRepo;
    private final AppUserRepository        userRepo;

    public JobService(JobRequestRepository jobRepo,
                      JobApplicationRepository appRepo,
                      WorkerProfileRepository workerRepo,
                      AppUserRepository userRepo) {
        this.jobRepo    = jobRepo;
        this.appRepo    = appRepo;
        this.workerRepo = workerRepo;
        this.userRepo   = userRepo;
    }

    // ── All Jobs (public) ────────────────────────────────────
    public List<JobResponse> listAll() {
        return jobRepo.findAll().stream().map(JobResponse::from).toList();
    }

    public JobResponse getById(Long id) {
        return JobResponse.from(jobRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job not found")));
    }

    // ── Employer: Create Job ─────────────────────────────────
    public JobResponse createJob(CreateJobRequest req, String email) {
        AppUser employer = getUser(email);
        if (!employer.isEmployer())
            throw new SecurityException("Only employers can post jobs");

        JobRequest job = new JobRequest();
        job.setEmployerId(employer.getId());
        job.setTitle(req.getTitle());
        job.setDescription(req.getDescription());
        job.setLocation(req.getLocation());
        job.setCategory(req.getCategory());
        job.setJobType(req.getJobType());
        job.setSalary(req.getSalary());
        job.setContactPhone(req.getContactPhone());
        job.setLatitude(req.getLatitude());
        job.setLongitude(req.getLongitude());
        job.setStatus(JobRequest.Status.OPEN);

        return JobResponse.from(jobRepo.save(job));
    }

    // ── Employer: My Jobs ────────────────────────────────────
    public List<JobResponse> getMyJobs(String email) {
        AppUser employer = getUser(email);
        return jobRepo.findByEmployerId(employer.getId())
                .stream().map(JobResponse::from).toList();
    }

    // ── Employer: View Applications ──────────────────────────
    public List<JobApplicationResponse> getApplications(Long jobId, String email) {
        JobRequest job = findJob(jobId);
        AppUser employer = getUser(email);
        if (!job.getEmployerId().equals(employer.getId()))
            throw new SecurityException("Not your job");

        return appRepo.findByJobId(jobId).stream().map(app -> {
            WorkerProfile wp = workerRepo.findById(app.getWorkerId()).orElse(null);
            JobApplicationResponse r = new JobApplicationResponse();
            r.setApplicationId(app.getId());
            r.setStatus(app.getStatus().name());
            r.setMessage(app.getMessage());
            r.setWorkerId(app.getWorkerId());
            if (wp != null) {
                r.setWorkerName(wp.getFullName());
                r.setSkill(wp.getSkillCategory());
                r.setLocation(wp.getLocation());
                r.setExperienceYears(wp.getExperienceYears());
                r.setDailyRate(wp.getDailyRate());
            }
            return r;
        }).toList();
    }

    // ── Employer: Accept/Reject ──────────────────────────────
    @Transactional
    public void decideApplication(Long appId, boolean accept, String email) {
        JobApplication app = appRepo.findById(appId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));
        JobRequest job = findJob(app.getJobId());
        AppUser employer = getUser(email);

        if (!job.getEmployerId().equals(employer.getId()))
            throw new SecurityException("Not authorized");

        if (accept) {
            app.setStatus(JobApplication.Status.ACCEPTED);
            job.setStatus(JobRequest.Status.ASSIGNED);
            job.setAssignedWorkerId(app.getWorkerId());
            jobRepo.save(job);
        } else {
            app.setStatus(JobApplication.Status.REJECTED);
        }
        appRepo.save(app);
    }

    // ── Employer: Complete Job ───────────────────────────────
    @Transactional
    public void completeJob(Long jobId, String email) {
        JobRequest job = findJob(jobId);
        AppUser employer = getUser(email);

        if (!job.getEmployerId().equals(employer.getId()))
            throw new SecurityException("Not authorized");
        if (job.getStatus() != JobRequest.Status.ASSIGNED)
            throw new IllegalStateException("Job is not assigned");

        job.setStatus(JobRequest.Status.COMPLETED);
        jobRepo.save(job);
    }

    // ── Worker: Apply ────────────────────────────────────────
    public void apply(Long jobId, String email, String message) {
        JobRequest job = findJob(jobId);
        if (job.getStatus() != JobRequest.Status.OPEN)
            throw new IllegalStateException("Job is not open for applications");

        AppUser user = getUser(email);
        if (!user.isWorker())
            throw new SecurityException("Only workers can apply");

        WorkerProfile profile = workerRepo.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("Create a worker profile first"));

        if (appRepo.existsByJobIdAndWorkerId(jobId, profile.getId()))
            throw new IllegalStateException("You already applied to this job");

        JobApplication app = new JobApplication();
        app.setJobId(jobId);
        app.setWorkerId(profile.getId());
        app.setMessage(message);
        appRepo.save(app);
    }

    // ── Worker: My Assigned Jobs ─────────────────────────────
    public List<JobResponse> getMyAssignedJobs(String email) {
        AppUser user = getUser(email);
        if (!user.isWorker()) throw new SecurityException("Only workers");

        WorkerProfile profile = workerRepo.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("Worker profile not found"));

        return jobRepo.findByAssignedWorkerId(profile.getId())
                .stream().map(JobResponse::from).toList();
    }

    // ── Worker: Mark Complete ────────────────────────────────
    @Transactional
    public void workerCompleteJob(Long jobId, String email) {
        JobRequest job = findJob(jobId);
        AppUser user   = getUser(email);

        WorkerProfile profile = workerRepo.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("Worker profile not found"));

        if (!profile.getId().equals(job.getAssignedWorkerId()))
            throw new SecurityException("Not assigned to this job");
        if (job.getStatus() != JobRequest.Status.ASSIGNED)
            throw new IllegalStateException("Job is not in ASSIGNED state");

        job.setStatus(JobRequest.Status.COMPLETED);
        jobRepo.save(job);
    }

    // ── Helpers ──────────────────────────────────────────────
    private AppUser getUser(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private JobRequest findJob(Long id) {
        return jobRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));
    }
}
