package com.careerconnect.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.careerconnect.dto.ApiResponse;
import com.careerconnect.dto.RecruiterProfileRequest;
import com.careerconnect.model.Company;
import com.careerconnect.model.RecruiterPreference;
import com.careerconnect.model.RecruiterProfile;
import com.careerconnect.repository.CompanyRepository;
import com.careerconnect.repository.RecruiterPreferenceRepository;
import com.careerconnect.repository.RecruiterProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruiterProfileService {

  private final RecruiterProfileRepository recruiterRepo;
  private final CompanyRepository companyRepo;
  private final RecruiterPreferenceRepository prefRepo;

  /* GET */

  public ApiResponse getProfile(Long userId) {

    RecruiterProfile recruiter = recruiterRepo.findByUserId(userId)
        .orElse(null);

    if (recruiter == null) {
      return new ApiResponse(false, "Profile not found", null);
    }

    Company company = companyRepo.findByRecruiterId(recruiter.getId())
        .orElse(null);

    RecruiterPreference pref = prefRepo.findByRecruiterId(recruiter.getId())
        .orElse(null);

    Map<String, Object> data = new HashMap<>();

    data.put("recruiter", recruiter);
    data.put("company", company);
    data.put("preferences", pref);

    return new ApiResponse(true, "Profile loaded", data);
  }

  /* SAVE */

  public ApiResponse saveProfile(Long userId,
      RecruiterProfileRequest req) {

    // 1. Recruiter
    RecruiterProfile recruiter = recruiterRepo.findByUserId(userId)
        .orElse(new RecruiterProfile());

    recruiter.setUserId(userId);
    recruiter.setName(req.getRecruiter().getName());
    recruiter.setEmail(req.getRecruiter().getEmail());
    recruiter.setPhone(req.getRecruiter().getPhone());
    recruiter.setPosition(req.getRecruiter().getPosition());
    recruiter.setAvatarUrl(req.getRecruiter().getAvatarUrl());

    recruiter = recruiterRepo.save(recruiter);

    // 2. Company
    Company company = companyRepo.findByRecruiterId(recruiter.getId())
        .orElse(new Company());

    company.setRecruiterId(recruiter.getId());
    company.setName(req.getCompany().getName());
    company.setIndustry(req.getCompany().getIndustry());
    company.setSize(req.getCompany().getSize());
    company.setWebsite(req.getCompany().getWebsite());
    company.setLocation(req.getCompany().getLocation());
    company.setDescription(req.getCompany().getDescription());

    companyRepo.save(company);

    // 3. Preferences
    RecruiterPreference pref = prefRepo.findByRecruiterId(recruiter.getId())
        .orElse(new RecruiterPreference());

    pref.setRecruiterId(recruiter.getId());

    pref.setRoles(
        String.join(",", req.getPreferences().getRoles()));

    pref.setExperienceLevels(
        String.join(",", req.getPreferences().getExperience()));

    pref.setLocations(
        String.join(",", req.getPreferences().getLocations()));

    prefRepo.save(pref);

    return new ApiResponse(true, "Profile saved", null);
  }
}
