package com.example.challengewithmebe.problem.controller;


import com.example.challengewithmebe.problem.model.ProblemDto;
import com.example.challengewithmebe.problem.model.ProblemListInfo;
import com.example.challengewithmebe.problem.service.ChallengesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/challenges")
public class ChallengesController {
    private final ChallengesService challengesService;




    @GetMapping
    public ResponseEntity<ProblemListInfo> getListProblems(
            @RequestParam(required = false) List<String> level,
            @RequestParam(required = false) List<String> type,
            @RequestParam(required = false) List<String> past,
            @RequestParam(value="sort", defaultValue = "createdAt") String sort,
            @RequestParam(value = "page",defaultValue= "0") int page,
            @RequestParam(value="size",defaultValue = "10") int size){


        ProblemListInfo problemsInfo = challengesService.getProblems(level,type,past, sort,page,size);
        return ResponseEntity.ok(problemsInfo);
    }




    @GetMapping("/{problemId}")
    public ResponseEntity<ProblemDto> getProblem(@PathVariable Long problemId) {
        ProblemDto problemDto = challengesService.getProblemWithTestcases(problemId);
        return ResponseEntity.ok(problemDto);
    }



}
