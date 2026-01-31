package com.careerconnect.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.careerconnect.model.Job;
import com.careerconnect.repository.JobRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService {

 private final JobRepository repo;


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
}
