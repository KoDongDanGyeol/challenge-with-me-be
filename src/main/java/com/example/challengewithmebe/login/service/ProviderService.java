package com.example.challengewithmebe.login.service;


import com.example.challengewithmebe.login.dto.OAuthProfile;
import com.example.challengewithmebe.login.dto.google.GoogleInfo;
import com.example.challengewithmebe.login.dto.google.GoogleProfile;
import com.example.challengewithmebe.login.OAuthRequestFactory;
import com.example.challengewithmebe.login.dto.request.OAuthRequest;
import com.example.challengewithmebe.login.dto.response.OAuthCodeResponse;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.naming.CommunicationException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderService {

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final OAuthRequestFactory oAuthRequestFactory;


    public OAuthProfile getProfile(String accessToken, String provider) throws CommunicationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        String profileUrl = oAuthRequestFactory.getProfileUrl(provider);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(profileUrl, request, String.class);
        //log.info(response.getBody());
        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                return extractProfile(response, provider);
            }
        } catch (Exception e) {
            throw new CommunicationException();
        }
        throw new CommunicationException();
    }

    private OAuthProfile extractProfile(ResponseEntity<String> response, String provider) {
        GoogleInfo googleInfo = gson.fromJson(response.getBody(), GoogleInfo.class);
        return GoogleProfile.builder()
                .providerId(googleInfo.getSub())
                .provider("google")
                .name(googleInfo.getName())
                .imageUrl(googleInfo.getPicture())
                .email(googleInfo.getEmail())
                .build();
    }

    public OAuthCodeResponse getAccessToken(String code, String provider) throws CommunicationException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        OAuthRequest oAuthRequest = oAuthRequestFactory.getRequest(code, provider);
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(oAuthRequest.getMap(), httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(oAuthRequest.getUrl(), request, String.class);
        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                return gson.fromJson(response.getBody(), OAuthCodeResponse.class);
            }
        } catch (Exception e) {
            throw new CommunicationException();
        }
        throw new CommunicationException();
    }


}