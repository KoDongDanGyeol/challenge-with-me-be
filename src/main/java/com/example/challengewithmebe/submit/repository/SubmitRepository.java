package com.example.challengewithmebe.submit.repository;

import com.example.challengewithmebe.submit.domain.Submit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SubmitRepository extends JpaRepository<Submit,Long> {
    // 문제를 맞힌 회원 수
    @Query("SELECT COUNT(DISTINCT s.memberId) FROM Submit s WHERE s.problemId = :problemId AND s.isCorrect = true")
    long countDistinctMemberIdByProblemIdAndIsCorrectTrue(@Param("problemId") Long problemId);

    // 총 제출 회원 수
    @Query("SELECT COUNT(s) FROM Submit s WHERE s.problemId = :problemId")
    long countByProblemId(@Param("problemId") Long problemId);


    @Query("SELECT COUNT(DISTINCT s.memberId) FROM Submit s WHERE s.problemId = :problemId")
    long countDistinctMemberIdByProblemId(@Param("problemId") Long problemId);

    //문제 맞힌 회원 수(여러번 푼 경우도 포함)
    @Query("SELECT COUNT(s) FROM Submit s WHERE s.problemId = :problemId AND s.isCorrect = true")
    long countByProblemIdAndIsCorrectTrue(@Param("problemId") Long problemId);




}
