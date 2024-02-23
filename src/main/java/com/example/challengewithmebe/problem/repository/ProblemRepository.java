package com.example.challengewithmebe.problem.repository;

import com.example.challengewithmebe.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
