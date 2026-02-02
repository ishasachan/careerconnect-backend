package com.careerconnect.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.careerconnect.dto.ApiResponse;
import com.careerconnect.dto.PostJobRequest;
import com.careerconnect.model.Job;
import com.careerconnect.repository.JobRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService {

 private final JobRepository repo;

  /* ===============================
       POST JOB
       =============================== */

    public ApiResponse postJob(Long recruiterId, PostJobRequest req){

        Job job = new Job();

        job.setRecruiterId(recruiterId);

        job.setTitle(req.getTitle());
        job.setCompany(req.getCompany());
        job.setLocation(req.getLocation());
        job.setSalary(req.getSalary());
        job.setType(req.getType());
        job.setDescription(req.getDescription());
        job.setRequirements(req.getRequirements());
        job.setDepartment(req.getDepartment());

        repo.save(job);

        return new ApiResponse(true,"Job posted successfully",job);
    }

    public ApiResponse updateJob(Long jobId, PostJobRequest req) {

    // 1. Find Job
    Job job = repo.findById(jobId).orElse(null);

    if (job == null) {
        return new ApiResponse(false, "Job not found", null);
    }

    // 2. Update Fields
    job.setTitle(req.getTitle());
    job.setCompany(req.getCompany());
    job.setLocation(req.getLocation());
    job.setSalary(req.getSalary());
    job.setType(req.getType());
    job.setDescription(req.getDescription());
    job.setRequirements(req.getRequirements());
    job.setDepartment(req.getDepartment());

    // 3. Save
    repo.save(job);

    return new ApiResponse(true, "Job updated successfully", job);
}


 /* ===============================
       GET RECRUITER JOBS
       =============================== */

    public ApiResponse getRecruiterJobs(Long recruiterId){

        List<Job> jobs =
            repo.findByRecruiterId(recruiterId);

        return new ApiResponse(true,"Jobs fetched",jobs);
    }

 // Get All Jobs
 public List<Job> getAll() {
   return repo.findAll();
 }


 // Get By ID
 public Job getById(Long id) {

   return repo.findById(id)
     .orElse(null);
 }


 // Search + Filter
 public List<Job> search(
   String keyword,
   String type,
   String location
 ) {

   List<Job> jobs;

   if (keyword != null && !keyword.isEmpty()) {

     jobs = repo
      .findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCaseOrLocationContainingIgnoreCase(
        keyword, keyword, keyword
      );

   } else {

     jobs = repo.findAll();
   }


   // Filter Type
   if (type != null && !type.isEmpty()) {

     jobs = jobs.stream()
       .filter(j -> type.equalsIgnoreCase(j.getType()))
       .toList();
   }


   // Filter Location
   if (location != null && !location.isEmpty()) {

     jobs = jobs.stream()
       .filter(j -> location.equalsIgnoreCase(j.getLocation()))
       .toList();
   }

   return jobs;
 }

 public ApiResponse pauseJob(Long jobId) {

    Job job = repo.findById(jobId).orElse(null);

    if (job == null) {
        return new ApiResponse(false, "Job not found", null);
    }

    job.setStatus("PAUSED");
    repo.save(job);

    return new ApiResponse(true, "Job paused successfully", job);
}

public ApiResponse closeJob(Long jobId) {

    Job job = repo.findById(jobId).orElse(null);

    if (job == null) {
        return new ApiResponse(false, "Job not found", null);
    }

    job.setStatus("CLOSED");
    repo.save(job);

    return new ApiResponse(true, "Job closed successfully", job);
}


public ApiResponse deleteJob(Long jobId) {

    Job job = repo.findById(jobId).orElse(null);

    if (job == null) {
        return new ApiResponse(false, "Job not found", null);
    }

    repo.delete(job);

    return new ApiResponse(true, "Job deleted successfully", null);
}
/* ===============================
   RESUME JOB
   =============================== */
public ApiResponse resumeJob(Long jobId) {

    Job job = repo.findById(jobId).orElse(null);

    if (job == null) {
        return new ApiResponse(false, "Job not found", null);
    }

    if (!"PAUSED".equals(job.getStatus())) {
        return new ApiResponse(false,
                "Only paused jobs can be resumed",
                job.getStatus());
    }

    job.setStatus("ACTIVE");
    repo.save(job);

    return new ApiResponse(true,
            "Job resumed successfully",
            job);
}


/* ===============================
   REOPEN JOB
   =============================== */
public ApiResponse reopenJob(Long jobId) {

    Job job = repo.findById(jobId).orElse(null);

    if (job == null) {
        return new ApiResponse(false, "Job not found", null);
    }

    if (!"CLOSED".equals(job.getStatus())) {
        return new ApiResponse(false,
                "Only closed jobs can be reopened",
                job.getStatus());
    }

    job.setStatus("ACTIVE");
    repo.save(job);

    return new ApiResponse(true,
            "Job reopened successfully",
            job);
}


}
