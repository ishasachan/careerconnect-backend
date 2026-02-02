package com.careerconnect.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.careerconnect.dto.ApiResponse;
import com.careerconnect.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RecommendationController {

    private final RecommendationService service;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getRecommendations(@PathVariable Long userId) {

        try {

            return ResponseEntity.ok(
                    new ApiResponse(
                            true,
                            "Recommendations fetched",
                            service.recommend(userId)));

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(
                    new ApiResponse(
                            false,
                            e.getMessage(),
                            null));
        }
    }
}
