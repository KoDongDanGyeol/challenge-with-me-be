package com.example.challengewithmebe.problem.service;


import com.example.challengewithmebe.problem.domain.Problem;
import com.example.challengewithmebe.problem.domain.Statistics;
import com.example.challengewithmebe.problem.domain.Testcase;
import com.example.challengewithmebe.problem.model.*;
import com.example.challengewithmebe.problem.repository.ProblemRepository;
import com.example.challengewithmebe.problem.repository.StatisticsRepository;
import com.example.challengewithmebe.problem.repository.TestcaseRepository;
import com.example.challengewithmebe.submit.repository.SubmitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ChallengesService {
    private final ProblemRepository problemRepository;
    private final TestcaseRepository testcaseRepository;
    private final SubmitRepository submitRepository;
    private final StatisticsRepository statisticsRepository;





    //챌린지 문제 목록 조회
    public ProblemListInfo getProblems(List<String> levels, List<String> type, List<String> pasts, String sort, int page, int size) {
        Sort sortCondition = Sort.by("createdAt").descending(); // 기본 정렬 조건
        if ("correctRateAsc".equals(sort)) {
            sortCondition = Sort.by("statistics.correctRate").ascending();
        } else if ("correctRateDesc".equals(sort)) {
            sortCondition = Sort.by("statistics.correctRate").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sortCondition);

        Page<Problem> problems;

        boolean levelsPresent = levels != null && !levels.isEmpty();
        boolean typesPresent = type != null && !type.isEmpty();
        boolean pastsPresent = pasts != null && !pasts.isEmpty();

        if (levelsPresent && typesPresent && pastsPresent) {
            problems = problemRepository.findByLevelInAndTypeInAndPastIn(levels, type, pasts, pageable);
        } else if (levelsPresent && typesPresent) {
            problems = problemRepository.findByLevelInAndTypeIn(levels, type, pageable);
        } else if (levelsPresent && pastsPresent) {
            problems = problemRepository.findByLevelInAndPastIn(levels, pasts, pageable);
        } else if (typesPresent && pastsPresent) {
            problems = problemRepository.findByTypeInAndPastIn(type, pasts, pageable);
        } else if (levelsPresent) {
            problems = problemRepository.findByLevelIn(levels, pageable);
        } else if (typesPresent) {
            problems = problemRepository.findByTypeIn(type, pageable);
        } else if (pastsPresent) {
            problems = problemRepository.findByPastIn(pasts, pageable);
        } else {
            problems = problemRepository.findAll(pageable);
        }

        for(Problem problem : problems){
            updateProblemStatistics(problem.getId());
        }


        List<String> allDistinctPasts = problemRepository.findDistinctPasts();

        Page<ProblemListDto> problemListDtos = problems.map(this::convertToDto);


        return new ProblemListInfo(problemListDtos, allDistinctPasts);
    }



    //정답률 계산, 완료한 사람 수
    private void updateProblemStatistics(Long problemId) {
        long totalSubmissions = submitRepository.countByProblemId(problemId);
        long correctSubmissions = submitRepository.countByProblemIdAndIsCorrectTrue(problemId);
        long correctUserCount = submitRepository.countDistinctMemberIdByProblemIdAndIsCorrectTrue(problemId);

        double correctRate = 0.0;
        if (totalSubmissions > 0) {
            correctRate = (double) correctSubmissions / totalSubmissions * 100;
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            correctRate = Double.parseDouble(decimalFormat.format(correctRate));
        } else {
            correctRate = 0.00;
        }

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(()-> new EntityNotFoundException("문제를 찾을 수 없습니다."));


        Statistics statistics = statisticsRepository.findByProblem_Id(problemId)
                .orElse(new Statistics());

        statistics.setProblem(problem);
        statistics.setCorrectRate(correctRate);
        statistics.setCompletedUserCount(correctUserCount);

        statisticsRepository.save(statistics);
    }


    //문제 상세 조회 및 공개 테스트 케이스, 히든 테스트 케이스 개수
    public ProblemDto getProblemWithTestcases(Long problemId) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new EntityNotFoundException("문제를 찾을 수 없습니다. " + problemId));




        List<Testcase> publicTestcases = testcaseRepository.findByProblemIdAndIsHiddenIsFalse(problemId);
        int hiddenCount = testcaseRepository.countByProblemIdAndIsHiddenIsTrue(problemId);


        List<TestCaseValues> testcaseValues = new ArrayList<>();
        Set<String> inputTypesSet = new LinkedHashSet<>();

        String outputType = null;
        for (Testcase testcase : publicTestcases) {
            List<String> inputValues = new ArrayList<>();
            String[] inputs = testcase.getInputData().split("#");
            String[] outputs = testcase.getOutputData().split(":");

            for (String s : inputs) {
                String[] input = s.split(":");
                inputTypesSet.add(input[0]); // 입력 타입 추가
                inputValues.add(input[1]); // 입력 값 추가
            }
            outputType = outputs[0];
            String expectedOutput = outputs[1];

            testcaseValues.add(new TestCaseValues(inputValues, expectedOutput));
        }

        List<String> inputTypes = new ArrayList<>(inputTypesSet);

        TestcaseTypes testcaseTypes = new TestcaseTypes(inputTypes, outputType);



        TestcaseInfo testcaseInfo = TestcaseInfo.builder()
                .testcaseTypes(testcaseTypes)
                .testcaseValues(testcaseValues)
                .hiddenTestcaseCount(hiddenCount)
                .build();

        ProblemDto problemDto = ProblemDto.builder()
                .id(problemId)
                .title(problem.getTitle())
                .type(problem.getType())
                .description(problem.getDescription())
                .past(problem.getPast())
                .testcases(Collections.singletonList(testcaseInfo))
                .build();

        return problemDto;





    }
    private ProblemListDto convertToDto(Problem problem) {
        Statistics statistics = statisticsRepository.findByProblem_Id(problem.getId())
                .orElseThrow(() -> new EntityNotFoundException("statistics를 찾을 수 없습니다."));

        return ProblemListDto.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .past(problem.getPast())
                .type(problem.getType())
                .level(problem.getLevel())
                .correctRate(statistics.getCorrectRate())
                .completedUserCount(statistics.getCompletedUserCount())
                .build();
           }


}

