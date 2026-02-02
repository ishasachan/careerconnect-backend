package com.careerconnect.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerconnect.dto.AuthResponse;
import com.careerconnect.dto.LoginRequest;
import com.careerconnect.dto.SignupRequest;
import com.careerconnect.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
  private final AuthService authService;

  // SIGNUP
  @PostMapping("/signup")
  public AuthResponse signup(@RequestBody SignupRequest req) {
    return authService.signup(req);
  }

  // LOGIN
  @PostMapping("/login")
  public AuthResponse login(@RequestBody LoginRequest req) {

    return authService.login(req);
  }
}
