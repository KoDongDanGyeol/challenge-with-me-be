package com.example.challengewithmebe.problem.repository;

import com.example.challengewithmebe.problem.domain.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestcaseRepository extends JpaRepository<Testcase,Long> {
    // 공개 테스트 케이스 조회
    List<Testcase> findByProblemIdAndIsHiddenIsFalse(Long problemId);

    // 비공개 테스트 케이스 조회
    List<Testcase> findByProblemIdAndIsHiddenIsTrue(Long problemId);

    //히든 테스트 케이스 개수 조회
    int countByProblemIdAndIsHiddenIsTrue(Long problemId);
}
