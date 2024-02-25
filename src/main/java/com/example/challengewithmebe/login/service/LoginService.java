package com.example.challengewithmebe.login.service;

import com.example.challengewithmebe.global.security.jwt.JwtProvider;
import com.example.challengewithmebe.member.domain.Member;
import com.example.challengewithmebe.member.domain.type.Role;
import com.example.challengewithmebe.login.dto.response.LoginResponse;
import com.example.challengewithmebe.login.dto.OAuthProfile;
import com.example.challengewithmebe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;


    public LoginResponse googleLogin(OAuthProfile oAuthProfile){
        Member member = memberRepository.findByEmailAndProvider(
                oAuthProfile.getEmail(),oAuthProfile.getProvider()).orElse(null);
        String accessToken;
        if(member!=null){ //이미 있는 사용자
           accessToken = jwtProvider.createAccessToken(String.valueOf(member.getId()));
        }else{//새 유저
            member = Member.builder()
                    .email(oAuthProfile.getEmail())
                    .name(oAuthProfile.getName())
                    .role(Role.USER)
                    .imgUrl(oAuthProfile.getImageUrl())
                    .provider(oAuthProfile.getProvider())
                    .build();

            memberRepository.save(member);
            accessToken = jwtProvider.createAccessToken(String.valueOf(member.getId()));
        }

        LoginResponse response = LoginResponse.builder()
                .name(member.getName())
                .email(member.getEmail())
                .accessToken(accessToken)
                .role(member.getRole().name())
                .build();
        return response;
    }
}
