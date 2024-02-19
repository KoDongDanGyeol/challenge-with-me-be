package com.example.challengewithmebe.member.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_GUEST"),
    ADMIN("ROLE_USER"),
    CONTRIBUTER("ROLE_CONTRIBUTER");
    private final String value;
}
