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

   if(job.getRequirements()==null || job.getRequirements().isEmpty()) continue;

   String requirements = job.getRequirements();
   List<String> jobSkills = new ArrayList<>();
   
   // Check if it's HTML or plain text
   if(requirements.contains("<") && requirements.contains(">")) {
       // HTML format - extract text and look for capitalized tech terms
       String plainText = requirements.replaceAll("<[^>]*>", " ");
       
       // Common tech skills to look for (case-insensitive)
       Set<String> techSkills = new HashSet<>(Arrays.asList(
           "java", "python", "javascript", "typescript", "react", "angular", "vue",
           "node", "nodejs", "spring", "springboot", "hibernate", "html", "css",
           "sql", "nosql", "mongodb", "postgresql", "mysql", "redis", "docker",
           "kubernetes", "aws", "azure", "gcp", "git", "rest", "graphql", "api",
           "microservices", "devops", "ci/cd", "jenkins", "maven", "gradle",
           "c++", "c#", "ruby", "php", "go", "rust", "kotlin", "swift",
           "figma", "sketch", "photoshop", "agile", "scrum", "jira",
           "webpack", "vite", "bootstrap", "tailwind", "sass", "less"
       ));
       
       // Extract words and match against known tech skills
       String[] words = plainText.toLowerCase().split("[\\s,;.()\\[\\]]+");
       for(String word : words) {
           String cleaned = word.trim();
           if(techSkills.contains(cleaned) && !jobSkills.contains(cleaned)) {
               jobSkills.add(cleaned);
           }
       }
   } else {
       // Plain text comma-separated format
       jobSkills = Arrays.stream(requirements.split(","))
           .map(String::trim)
           .map(String::toLowerCase)
           .filter(s -> !s.isEmpty())
           .toList();
   }

   if(jobSkills.isEmpty()) continue;

   long matched =
    jobSkills.stream()
     .filter(jobSkill -> userSkills.stream()
         .anyMatch(userSkill -> 
             userSkill.equals(jobSkill) || 
             userSkill.contains(jobSkill) || 
             jobSkill.contains(userSkill)))
     .count();

   int score =
    (int)((matched * 100.0) / jobSkills.size());


   // Only recommend if >30%
   if(score >= 30){

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
