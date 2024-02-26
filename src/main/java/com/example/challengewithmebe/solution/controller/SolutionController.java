package com.example.challengewithmebe.solution.controller;

import com.example.challengewithmebe.global.security.jwt.JwtProvider;
import com.example.challengewithmebe.solution.dto.SolutionDTO;
import com.example.challengewithmebe.solution.service.SolutionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/solutions")
@RequiredArgsConstructor
public class SolutionController {

    private final SolutionService solutionService;
    private final JwtProvider jwtProvider;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<SolutionDTO>> getAllSolutions(){
        List<SolutionDTO> solutions= solutionService.allCorrectSolutions();
        return ResponseEntity.ok(solutions);
    }

    @GetMapping("/{problemId}")
    public ResponseEntity<List<SolutionDTO>> getAllSolutions(@PathVariable Long problemId){
        List<SolutionDTO> solutions= solutionService.allCorrectSpecificSolutions(problemId);
        return ResponseEntity.ok(solutions);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getSolutionCounts(){
        Map<String, Long> count = new HashMap<>();
        count.put("count",solutionService.solutionCounts());
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{problemId}/group")
    public ResponseEntity<Page<SolutionDTO>> getSolutionPages(
            @PathVariable Long problemId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "java") String language,
            @RequestParam(defaultValue = "all") String type,
            HttpServletRequest request) {
        Long memberId = 0L;
        if(type.equals("my")){
            jwtProvider.isLoggedIn(request);
            memberId = Long.valueOf(jwtProvider.extractId(request));
        }
        Page<SolutionDTO> solutions = solutionService.findSolutionPages(page, size, language, type, problemId, memberId);
        return ResponseEntity.ok(solutions);
    }

}
