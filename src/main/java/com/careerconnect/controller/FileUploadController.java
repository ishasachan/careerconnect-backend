package com.careerconnect.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.careerconnect.service.SupabaseStorageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://careerconnect-frontend-r3mj.onrender.com"})
public class FileUploadController {

  private final SupabaseStorageService storage;

  // Upload Resume
  @PostMapping("/resume")
  public ResponseEntity<?> uploadResume(
      @RequestParam("file") MultipartFile file) {

    try {

      validateResume(file);

      String url = storage.uploadFile(file, "resumes");

      return ResponseEntity.ok(
          Map.of(
              "success", true,
              "url", url));

    } catch (Exception e) {

      return ResponseEntity.badRequest().body(
          Map.of(
              "success", false,
              "error", e.getMessage()));
    }
  }

  // Upload Avatar
  @PostMapping("/avatar")
  public ResponseEntity<?> uploadAvatar(
      @RequestParam("file") MultipartFile file) {

    try {

      validateImage(file);

      String url = storage.uploadFile(file, "avatars");

      return ResponseEntity.ok(
          Map.of(
              "success", true,
              "url", url));

    } catch (Exception e) {

      return ResponseEntity.badRequest().body(
          Map.of(
              "success", false,
              "error", e.getMessage()));
    }
  }

  // ---------- VALIDATION ----------

  private void validateResume(MultipartFile file) {

    if (file.isEmpty()) {
      throw new RuntimeException("File is empty");
    }

    if (file.getSize() > 10 * 1024 * 1024) {
      throw new RuntimeException("Max size 10MB");
    }

    String type = file.getContentType();

    if (!type.equals("application/pdf") &&
        !type.contains("word")) {

      throw new RuntimeException("Only PDF/DOC allowed");
    }
  }

  private void validateImage(MultipartFile file) {

    if (file.isEmpty()) {
      throw new RuntimeException("File is empty");
    }

    if (file.getSize() > 5 * 1024 * 1024) {
      throw new RuntimeException("Max size 5MB");
    }

    if (!file.getContentType().startsWith("image/")) {
      throw new RuntimeException("Only images allowed");
    }
  }
}
