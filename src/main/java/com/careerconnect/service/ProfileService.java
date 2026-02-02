package com.careerconnect.service;

import org.springframework.stereotype.Service;

import com.careerconnect.dto.ProfileRequest;
import com.careerconnect.model.User;
import com.careerconnect.model.UserProfile;
import com.careerconnect.repository.UserProfileRepository;
import com.careerconnect.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

  private final UserRepository userRepo;
  private final UserProfileRepository profileRepo;

  // SAVE / UPDATE PROFILE
  public UserProfile saveProfile(ProfileRequest req) {

    // ✅ Check userId
    if (req.getUserId() == null) {
      throw new RuntimeException("User ID is required");
    }

    // ✅ Check if user exists
    User user = userRepo.findById(req.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    // ✅ Find existing profile or create new
    UserProfile profile = profileRepo.findByUser(user).orElse(new UserProfile());

    profile.setUser(user);
    profile.setBio(req.getBio());
    profile.setSkills(req.getSkills());
    profile.setResumeUrl(req.getResumeUrl());
    profile.setAvatarUrl(req.getAvatarUrl());
    profile.setAiFeedback(req.getAiFeedback());

    return profileRepo.save(profile);
  }

  // GET PROFILE
  public UserProfile getProfile(Long userId) {

    if (userId == null) {
      throw new RuntimeException("User ID is required");
    }

    User user = userRepo.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    return profileRepo.findByUser(user)
        .orElse(null);
  }
}
