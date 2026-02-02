package com.careerconnect.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SupabaseStorageService {

  @Value("${supabase.url}")
  private String supabaseUrl;

  @Value("${supabase.key}")
  private String supabaseKey;

  // Upload File to Supabase
  public String uploadFile(MultipartFile file, String bucket)
      throws IOException {

    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

    String uploadUrl = supabaseUrl + "/storage/v1/object/" +
        bucket + "/" + fileName;

    HttpHeaders headers = new HttpHeaders();

    headers.set("Authorization", "Bearer " + supabaseKey);
    headers.set("apikey", supabaseKey);
    headers.setContentType(
        MediaType.parseMediaType(file.getContentType()));

    HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes(), headers);

    RestTemplate rest = new RestTemplate();

    ResponseEntity<String> response = rest.exchange(
        uploadUrl,
        HttpMethod.POST,
        entity,
        String.class);

    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new RuntimeException("Upload failed");
    }

    // Public URL
    return supabaseUrl +
        "/storage/v1/object/public/" +
        bucket + "/" + fileName;
  }
}
