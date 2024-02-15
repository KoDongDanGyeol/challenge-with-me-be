package com.example.challengewithmebe.login.dto.google;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleInfo {
    private String sub;
    private String provider;
    private String name;
    private String email;
    private String picture;
}
