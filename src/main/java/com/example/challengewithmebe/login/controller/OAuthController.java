package com.example.challengewithmebe.login.controller;

import com.example.challengewithmebe.login.dto.google.GoogleProfile;
import com.example.challengewithmebe.login.dto.response.LoginResponse;
import com.example.challengewithmebe.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.CommunicationException;

@RestController
@RequiredArgsConstructor
public class OAuthController {
    private final LoginService loginService;

    @GetMapping("/")
    public ResponseEntity<String> main(){
        String response = "hello";
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/oauth/login/google")
    public ResponseEntity<LoginResponse> googleSignup(@RequestParam("name") String name,
                                                      @RequestParam("email") String email,
                                                      @RequestParam("img") String img) throws CommunicationException {
        GoogleProfile googleProfile = GoogleProfile.builder()
                .provider("google")
                .email(email)
                .imageUrl(img)
                .name(name)
                .build();
        LoginResponse response = loginService.googleLogin(googleProfile);

        return ResponseEntity.ok().body(response);
    }
}
