package com.example.challengewithmebe.global.exception;

import com.example.challengewithmebe.global.exception.dto.FailResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResponseService {
    public FailResult getFailResult(String code, String message){
        return new FailResult(code,message);
    }
}
