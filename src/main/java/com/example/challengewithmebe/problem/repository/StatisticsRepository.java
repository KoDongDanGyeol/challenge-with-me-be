package com.example.challengewithmebe.problem.repository;

import com.example.challengewithmebe.problem.domain.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics,Long> {
    Optional<Statistics> findByProblem_Id(Long problemId);
}
