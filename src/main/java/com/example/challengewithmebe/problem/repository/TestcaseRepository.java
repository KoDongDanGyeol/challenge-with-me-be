package com.example.challengewithmebe.problem.repository;

import com.example.challengewithmebe.problem.domain.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {
}
