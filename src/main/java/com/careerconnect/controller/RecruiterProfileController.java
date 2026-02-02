package com.careerconnect.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerconnect.dto.ApiResponse;
import com.careerconnect.dto.RecruiterProfileRequest;
import com.careerconnect.service.RecruiterProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recruiter/profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RecruiterProfileController {

    private final RecruiterProfileService service;

    /* Get Profile */
    @GetMapping("/{userId}")
    public ApiResponse getProfile(@PathVariable Long userId) {

        return service.getProfile(userId);
    }

    /* Save / Update */
    @PostMapping("/{userId}")
    public ApiResponse saveProfile(
            @PathVariable Long userId,
            @RequestBody RecruiterProfileRequest request) {

        return service.saveProfile(userId, request);
    }
}
