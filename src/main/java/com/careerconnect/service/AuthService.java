package com.careerconnect.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.careerconnect.config.JwtUtil;
import com.careerconnect.dto.AuthResponse;
import com.careerconnect.dto.LoginRequest;
import com.careerconnect.dto.SignupRequest;
import com.careerconnect.model.User;
import com.careerconnect.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repo;
 private final BCryptPasswordEncoder encoder;
 private final JwtUtil jwt;

 // SIGNUP
 public AuthResponse signup(SignupRequest req) {

   if (repo.existsByEmail(req.getEmail())) {
     throw new RuntimeException("Email already exists");
   }

   User user = new User();

   user.setName(req.getName());
   user.setEmail(req.getEmail());
   user.setPassword(encoder.encode(req.getPassword()));
   user.setRole(req.getRole());

   repo.save(user);

   String token = jwt.generateToken(user.getEmail());

   return new AuthResponse(
     token,
     user.getRole(),
     user.getEmail()
   );
 }

 // LOGIN
 public AuthResponse login(LoginRequest req) {

   User user = repo.findByEmail(req.getEmail())
     .orElseThrow(() -> new RuntimeException("User not found"));

   if (!encoder.matches(req.getPassword(), user.getPassword())) {
     throw new RuntimeException("Wrong password");
   }

   String token = jwt.generateToken(user.getEmail());

   return new AuthResponse(
     token,
     user.getRole(),
     user.getEmail()
   );
 }
}
