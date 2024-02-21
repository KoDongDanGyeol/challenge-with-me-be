package com.example.challengewithmebe.member.controller;

import com.example.challengewithmebe.global.security.jwt.JwtProvider;
import com.example.challengewithmebe.member.dto.MemberDTO;
import com.example.challengewithmebe.member.dto.request.EditName;
import com.example.challengewithmebe.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @GetMapping("/user/mypage")
    public ResponseEntity<MemberDTO> getMemberInfo(HttpServletRequest request){
        Long memberId = Long.valueOf(jwtProvider.extractId(request));
        MemberDTO response = memberService.getInfo(memberId);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/user/profile")
    public ResponseEntity<MemberDTO>editName(@RequestBody EditName editName, HttpServletRequest request){
        Long memberId = Long.valueOf(jwtProvider.extractId(request));
        MemberDTO response = memberService.editName(editName.getName(), memberId);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/user/withdrawal")
    public ResponseEntity<String> withdrawal(HttpServletRequest request){
        Long memberId = Long.valueOf(jwtProvider.extractId(request));
        memberService.withdrawal(memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
