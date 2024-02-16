package com.example.challengewithmebe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChallengeWithMeBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChallengeWithMeBeApplication.class, args);
    }

}
