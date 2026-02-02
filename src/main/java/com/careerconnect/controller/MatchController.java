package com.careerconnect.controller;

import org.springframework.web.bind.annotation.*;

import com.careerconnect.dto.*;
import com.careerconnect.service.MatchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://careerconnect-frontend-r3mj.onrender.com"})
public class MatchController {

    private final MatchService service;

    @PostMapping("/check")
    public ApiResponse checkMatch(@RequestBody MatchRequest req) {

        return service.checkMatch(req);
    }
}
