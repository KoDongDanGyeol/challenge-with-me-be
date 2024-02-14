package com.example.challengewithmebe.solution.service;

import com.example.challengewithmebe.solution.dto.SolutionDTO;
import com.example.challengewithmebe.solution.repository.SolutionRepository;
import com.example.challengewithmebe.submit.domain.Submit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SolutionService {

    private final SolutionRepository solutionRepository;

    // 모든 풀이 조회
    public List<SolutionDTO> allCorrectSolutions(){
        List<Object[]> allSolutions = solutionRepository.findByIsCorrectTrue();
        return convertToSolutionDTOList(allSolutions);
    }

    // 특정 문제에 대해 풀이 조회
    public List<SolutionDTO> allCorrectSpecificSolutions(Long problemId){
        List<Object[]> byIsCorrectTrue = solutionRepository.findByIsCorrectTrueAndProblemId(problemId);
        return convertToSolutionDTOList(byIsCorrectTrue);
    }

    // 풀이 개수 조회
    public Long solutionCounts() {
        Long count =solutionRepository.countByIsCorrectTrue();
        return count;
    }

    // 특정 문제에 대한 풀이 페이지 조회
    public Page<SolutionDTO> findSolutionPages(int page, int size, String language, String type, Long problemId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modifiedAt").descending());
        Page<Object[]> solutions;
        if(type.equals("my")){
            Long memberId = 0L;
            solutions = solutionRepository.
                    findByIsCorrectTrueAndLanguageAndMemberIdAndProblemId(language, memberId, problemId, pageable);
        } else {
            solutions = solutionRepository.
                    findByIsCorrectTrueAndLanguageAndProblemId(language,problemId, pageable);
        }
        return convertToSolutionDTOPage(solutions, pageable);
    }






    private List<SolutionDTO> convertToSolutionDTOList(List<Object[]> submits){
        List<SolutionDTO> solutionDTOs = new ArrayList<>();
        for(Object[] submit : submits){
            solutionDTOs.add(convertToSolutionDTO(submit));
        }

        return solutionDTOs;
    }

    private Page<SolutionDTO> convertToSolutionDTOPage(Page<Object[]> submits, Pageable pageable) {
        List<SolutionDTO> dtos = submits.stream()
                .map(this::convertToSolutionDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, submits.getTotalElements());
    }

    //submit을 solutionDTO로 바꾸는 메서드
    private SolutionDTO convertToSolutionDTO(Object[] queryResult) {
        // queryResult[0]은 Submit 객체, queryResult[1]은 회원 이름, queryResult[2]은 프로필 이미지 링크입니다.
        Submit submit = (Submit) queryResult[0];
        String name = (String) queryResult[1];
        String imgUrl = (String) queryResult[2];

        return SolutionDTO.builder()
                .id(submit.getId())
                .problemId(submit.getProblemId())
                .memberId(submit.getMemberId())
                .isCorrect(submit.isCorrect())
                .submitCode(submit.getSubmitCode())
                .language(submit.getLanguage())
                .status(submit.getStatus())
                .createdAt(submit.getCreatedAt())
                .modifiedAt(submit.getModifiedAt())
                .name(name) // 쿼리 결과에서 회원 이름을 설정
                .imgUrl(imgUrl) // 쿼리 결과에서 프로필 이미지 링크를 설정
                .build();

    }


}
