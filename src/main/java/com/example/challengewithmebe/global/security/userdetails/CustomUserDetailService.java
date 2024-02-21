package com.example.challengewithmebe.global.security.userdetails;

import com.example.challengewithmebe.global.exception.notExist.NotExistMemberException;
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
        Member member = memberRepository.findById(Long.parseLong(id)).orElseThrow(NotExistMemberException::new);
        return new CustomUserDetails(member);
    }
}
