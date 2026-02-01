package com.careerconnect.dto;

import java.util.List;

import lombok.Data;

@Data
public class RecruiterProfileRequest {

    private Recruiter recruiter;
    private Company company;
    private Preferences preferences;

    @Data
    public static class Recruiter {
        private String name;
        private String email;
        private String phone;
        private String position;
        private String avatarUrl;
    }

    @Data
    public static class Company {
        private String name;
        private String industry;
        private String size;
        private String website;
        private String location;
        private String description;
    }

    @Data
    public static class Preferences {
        private List<String> roles;
        private List<String> experience;
        private List<String> locations;
    }
}
