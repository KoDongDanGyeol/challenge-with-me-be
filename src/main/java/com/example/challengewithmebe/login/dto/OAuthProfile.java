package com.example.challengewithmebe.login.dto;

public interface OAuthProfile {
    String getProvider();
    String getEmail();
    String getName();
    String getImageUrl();
}
