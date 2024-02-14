package com.example.challengewithmebe.problem.repository;

import com.example.challengewithmebe.problem.domain.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {
    List<Testcase> findByProblemIdAndHiddenIsTrue(Long problemId);
    List<Testcase> findByProblemIdAndHiddenIsFalse(Long problemId);
}
