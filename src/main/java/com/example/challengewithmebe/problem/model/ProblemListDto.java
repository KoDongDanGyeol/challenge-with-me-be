package com.example.challengewithmebe.problem.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class ProblemListDto {
    private Long id;
    private String title;
    private String past;
    private String type;
    private String level;
    private double correctRate;
    private Long completedUserCount;



}
