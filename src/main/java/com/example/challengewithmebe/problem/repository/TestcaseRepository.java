package com.example.challengewithmebe.problem.repository;

import com.example.challengewithmebe.problem.domain.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {
    List<Testcase> findByProblemIdAndIsHiddenIsTrue(Long problemId);
    List<Testcase> findByProblemIdAndIsHiddenIsFalse(Long problemId);
}
