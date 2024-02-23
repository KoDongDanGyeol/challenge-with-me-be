package com.example.challengewithmebe.login.controller;

import com.example.challengewithmebe.login.dto.OAuthProfile;
import com.example.challengewithmebe.login.dto.response.LoginResponse;
import com.example.challengewithmebe.login.service.LoginService;
import com.example.challengewithmebe.login.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.CommunicationException;

@RestController
@RequiredArgsConstructor
public class OAuthController {
    
    private final ProviderService providerService;
    private final LoginService loginService;

    @GetMapping("/")
    public ResponseEntity<String> main(){
        String response = "hello";
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/oauth/login/google")
    public ResponseEntity<LoginResponse> googleSignup(@RequestParam("code") String code) throws CommunicationException {
        String accessToken = providerService.getAccessToken(code, "google").getAccess_token();
        OAuthProfile oAuthProfile = providerService.getProfile(accessToken,"google");
        LoginResponse response = loginService.googleLogin(oAuthProfile);

        return ResponseEntity.ok().body(response);
    }
}
