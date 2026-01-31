package com.careerconnect.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.careerconnect.dto.ApiResponse;
import com.careerconnect.model.Job;
import com.careerconnect.service.JobService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class JobController {

 private final JobService service;


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
}
