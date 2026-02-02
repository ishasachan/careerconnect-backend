package com.careerconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.careerconnect.dto.ApiResponse;
import com.careerconnect.dto.PostJobRequest;
import com.careerconnect.model.Job;
import com.careerconnect.service.JobService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class JobController {

 private final JobService service;


  /* ===============================
       POST JOB
       =============================== */

    @PostMapping("/recruiter/{recruiterId}")
    public ApiResponse postJob(
        @PathVariable Long recruiterId,
        @RequestBody PostJobRequest request){

        return service.postJob(recruiterId, request);
    }

/* ===============================
   UPDATE JOB
   =============================== */

@PutMapping("/{jobId}")
public ApiResponse updateJob(
        @PathVariable Long jobId,
        @RequestBody PostJobRequest request) {

    return service.updateJob(jobId, request);
}

    /* ===============================
       GET MY JOBS
       =============================== */

    @GetMapping("/recruiter/{recruiterId}")
    public ApiResponse getMyJobs(
        @PathVariable Long recruiterId){

        return service.getRecruiterJobs(recruiterId);
    }

 // LIST + SEARCH
 @GetMapping
 public ResponseEntity<ApiResponse> getJobs(

   @RequestParam(required = false) String keyword,
   @RequestParam(required = false) String type,
   @RequestParam(required = false) String location
 ) {

   List<Job> jobs =
     service.search(keyword, type, location);

   return ResponseEntity.ok(
     new ApiResponse(
       true,
       "Jobs fetched",
       jobs
     )
   );
 }


 // DETAILS
 @GetMapping("/{id}")
 public ResponseEntity<ApiResponse> getJob(@PathVariable Long id) {

   Job job = service.getById(id);

   if (job == null) {

     return ResponseEntity.ok(
       new ApiResponse(
         false,
         "Job not found",
         null
       )
     );
   }

   return ResponseEntity.ok(
     new ApiResponse(
       true,
       "Job fetched",
       job
     )
   );
 }

 @PatchMapping("/{id}/pause")
public ApiResponse pauseJob(@PathVariable Long id) {
    return service.pauseJob(id);
}
@PatchMapping("/{id}/close")
public ApiResponse closeJob(@PathVariable Long id) {
    return service.closeJob(id);
}
@DeleteMapping("/{id}")
public ApiResponse deleteJob(@PathVariable Long id) {
    return service.deleteJob(id);
}

/* ===============================
   RESUME JOB (From PAUSED → ACTIVE)
   =============================== */
@PatchMapping("/{id}/resume")
public ApiResponse resumeJob(@PathVariable Long id) {

    return service.resumeJob(id);
}


/* ===============================
   REOPEN JOB (From CLOSED → ACTIVE)
   =============================== */
@PatchMapping("/{id}/reopen")
public ApiResponse reopenJob(@PathVariable Long id) {

    return service.reopenJob(id);
}


}
