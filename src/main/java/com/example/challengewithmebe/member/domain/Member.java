package com.example.challengewithmebe.member.domain;

import com.example.challengewithmebe.global.app.domain.BaseEntity;
import com.example.challengewithmebe.member.domain.type.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imgUrl; //프로필 이미지 링크
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String provider; //로그인 종류
    private String providerId; //소셜 로그인 시 부여되는 id
}
