package com.example.challengewithmebe.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
    private Long id;
    private String name;
    private String imgUrl; //프로필 이미지 링크
    private String email;
}
