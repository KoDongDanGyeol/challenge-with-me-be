package com.example.challengewithmebe.global.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FailResult {
    private String errorCode;
    private String message;
}
