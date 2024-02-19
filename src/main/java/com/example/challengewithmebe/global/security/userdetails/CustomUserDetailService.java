package com.example.challengewithmebe.global.security.userdetails;

import com.example.challengewithmebe.member.domain.Member;
import com.example.challengewithmebe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(id)).orElseThrow();
        //todo : 에러코드 추가 필요
        return new CustomUserDetails(member);
    }
}
