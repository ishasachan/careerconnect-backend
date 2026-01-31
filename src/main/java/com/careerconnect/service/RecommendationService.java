package com.careerconnect.service;

import java.util.*;
import org.springframework.stereotype.Service;

import com.careerconnect.model.Job;
import com.careerconnect.model.Profile;
import com.careerconnect.repository.JobRepository;
import com.careerconnect.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {

 private final JobRepository jobRepo;
 private final ProfileRepository profileRepo;


 public List<Map<String,Object>> recommend(Long userId){

  // Get profile
  Profile profile =
   profileRepo.findByUserId(userId)
   .orElseThrow(() ->
    new RuntimeException("Profile not found"));

  // Get skills
  List<String> userSkills =
   Arrays.stream(profile.getSkills().split(","))
   .map(String::trim)
   .map(String::toLowerCase)
   .toList();

  List<Job> jobs = jobRepo.findAll();

  List<Map<String,Object>> result =
   new ArrayList<>();


  for(Job job : jobs){

   if(job.getRequirements()==null) continue;

   List<String> jobSkills =
    Arrays.stream(job.getRequirements().split(","))
    .map(String::trim)
    .map(String::toLowerCase)
    .toList();

   if(jobSkills.isEmpty()) continue;

   long matched =
    jobSkills.stream()
     .filter(userSkills::contains)
     .count();

   int score =
    (int)((matched * 100.0) / jobSkills.size());


   // Only recommend if >50%
   if(score >= 50){

    Map<String,Object> map =
     new HashMap<>();

    map.put("jobId", job.getId());
    map.put("title", job.getTitle());
    map.put("company", job.getCompany());
    map.put("location", job.getLocation());
    map.put("salary", job.getSalary());

    map.put("match", score);

    map.put("skills", jobSkills);

    result.add(map);
   }
  }

  // Sort by highest match
  result.sort((a,b) ->
   (int)b.get("match") -
   (int)a.get("match"));

  return result;
 }
}
