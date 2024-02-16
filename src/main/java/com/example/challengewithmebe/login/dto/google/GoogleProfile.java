package com.example.challengewithmebe.login.dto.google;

import com.example.challengewithmebe.login.dto.OAuthProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleProfile implements OAuthProfile {
    private String providerId;
    private String provider;
    private String name;
    private String email;
    private String imageUrl;
}
