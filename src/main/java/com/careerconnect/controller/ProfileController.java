package com.careerconnect.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerconnect.dto.ApiResponse;
import com.careerconnect.dto.ProfileRequest;
import com.careerconnect.model.UserProfile;
import com.careerconnect.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProfileController {
     private final ProfileService service;


// SAVE / UPDATE PROFILE
 @PostMapping(
   value = "/save",
   consumes = "application/json",
   produces = "application/json"
 )
 public ResponseEntity<ApiResponse> save(
     @RequestBody ProfileRequest req) {

   try {

     UserProfile profile = service.saveProfile(req);

     return ResponseEntity.ok(
       new ApiResponse(
         true,
         "Profile saved successfully",
         profile
       )
     );

   } catch (Exception e) {

     return ResponseEntity.badRequest().body(
       new ApiResponse(
         false,
         e.getMessage(),
         null
       )
     );
   }
 }


 // GET PROFILE
 @GetMapping(
   value = "/{userId}",
   produces = "application/json"
 )
 public ResponseEntity<ApiResponse> get(
     @PathVariable Long userId) {

   try {

     UserProfile profile = service.getProfile(userId);

     // ✅ If profile not created yet
     if (profile == null) {

       return ResponseEntity.ok(
         new ApiResponse(
           true,
           "Profile not created yet",
           null
         )
       );
     }

     return ResponseEntity.ok(
       new ApiResponse(
         true,
         "Profile fetched successfully",
         profile
       )
     );

   } catch (Exception e) {

     return ResponseEntity.badRequest().body(
       new ApiResponse(
         false,
         e.getMessage(),
         null
       )
     );
   }
 }
}
