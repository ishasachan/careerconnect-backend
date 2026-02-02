package com.careerconnect.service;

import org.springframework.stereotype.Service;

import com.careerconnect.dto.*;
import com.careerconnect.model.*;
import com.careerconnect.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final JobRepository jobRepo;
    private final JobMatchRepository matchRepo;
    private final ProfileRepository profileRepo; // your profile table

    public ApiResponse checkMatch(MatchRequest req) {

        // 1. Get Job
        Job job = jobRepo
                .findById(req.getJobId())
                .orElse(null);

        if (job == null) {
            return new ApiResponse(false, "Job not found", null);
        }

        // 2. Get User Profile
        Profile profile =
                profileRepo.findByUserId(req.getUserId())
                .orElse(null);

        if (profile == null) {
            return new ApiResponse(false, "Profile not found", null);
        }

        // 3. Calculate Scores (Simple Logic)
        int skillScore = calculateSkillMatch(
                profile.getSkills(),
                job.getRequirements()
        );

        int experienceScore =
                calculateExperienceMatch(
                        profile.getExperienceYears()
                );

        int profileScore =
                profile.getBio() != null ? 80 : 50;

        int finalScore =
                (skillScore + experienceScore + profileScore) / 3;

        // 4. Feedback
        String feedback =
                generateFeedback(finalScore);

        // 5. Save
        JobMatch match =
                matchRepo
                .findByUserIdAndJobId(
                        req.getUserId(),
                        req.getJobId()
                )
                .orElse(new JobMatch());

        match.setUserId(req.getUserId());
        match.setJobId(req.getJobId());
        match.setMatchScore(finalScore);
        match.setSkillScore(skillScore);
        match.setExperienceScore(experienceScore);
        match.setProfileScore(profileScore);
        match.setFeedback(feedback);

        matchRepo.save(match);

        // 6. Response
        MatchResponse res = new MatchResponse();

        res.setMatchScore(finalScore);
        res.setSkillScore(skillScore);
        res.setExperienceScore(experienceScore);
        res.setProfileScore(profileScore);
        res.setFeedback(feedback);

        return new ApiResponse(true, "Match calculated", res);
    }

    /* =============================
       SCORING LOGIC
       ============================= */

    private int calculateSkillMatch(
            String userSkills,
            String jobReq) {

        if (userSkills == null || jobReq == null)
            return 40;

        String[] u = userSkills.split(",");
        String[] j = jobReq.split(",");

        int matched = 0;

        for (String us : u) {
            for (String js : j) {
                if (us.trim()
                      .equalsIgnoreCase(js.trim())) {
                    matched++;
                }
            }
        }

        return Math.min(100,
                (matched * 100) / j.length);
    }

    private int calculateExperienceMatch(
            Integer exp) {

        if (exp == null) return 50;

        if (exp >= 5) return 100;
        if (exp >= 3) return 80;
        if (exp >= 1) return 60;

        return 40;
    }

    private String generateFeedback(int score) {

        if (score >= 85)
            return "Excellent match! Strong candidate.";

        if (score >= 70)
            return "Good match. Consider applying.";

        if (score >= 50)
            return "Average match. Improve skills.";

        return "Low match. Upskill recommended.";
    }
}
