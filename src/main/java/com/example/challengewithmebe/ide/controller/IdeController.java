package com.example.challengewithmebe.ide.controller;

import com.example.challengewithmebe.ide.model.*;
import com.example.challengewithmebe.problem.service.TestcaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenges")
public class IdeController {

    private final TestcaseService testcaseService;

    @PostMapping("/{problemId}/run")
    public ResponseEntity<RunResultDto> run(@RequestBody Map<String, Object> input, @PathVariable(name = "problemId") Long problemId) throws Exception {
        List<RunResult> result = testcaseService.run(problemId, input.get("code").toString());
        return ResponseEntity.ok(new RunResultDto("run", result));
    }


    @PostMapping("/{problemId}/submit")
    public ResponseEntity<SubmitResultDto> submit(@RequestBody Map<String, Object> input, @PathVariable(name = "problemId") Long problemId) throws Exception {
        List<RunResult> result = testcaseService.run(problemId, input.get("code").toString());
        List<SubmitResult> submits = testcaseService.submit(problemId, input.get("code").toString());
        return ResponseEntity.ok(new SubmitResultDto("submit", result, submits));
    }

    @GetMapping("/{problemId}/testcase")
    public ResponseEntity<ParamDto> getParamAndTypes(@PathVariable(name = "problemId") Long problemId) {
        return ResponseEntity.ok(testcaseService.getParamTypesAndTestcases(problemId));
    }
}