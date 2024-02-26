package com.example.challengewithmebe.problem.repository;

import com.example.challengewithmebe.problem.domain.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem,Long> {
  @Query("SELECT DISTINCT p.past FROM Problem p")
  List<String> findDistinctPasts();

    //전체
    Page<Problem> findAll(Pageable pageable);

    //난이도만
    Page<Problem> findByLevelIn(List<String> levels, Pageable pageable);

    //유형만
    Page<Problem> findByTypeIn(List<String> type, Pageable pageable);

    //기출만
    Page<Problem> findByPastIn(List<String> pasts, Pageable pageable);

    //난이도, 유형만
    Page<Problem> findByLevelInAndTypeIn(List<String> levels, List<String> types, Pageable pageable);

    //난이도, 기출만
    Page<Problem> findByLevelInAndPastIn(List<String> levels, List<String> pasts, Pageable pageable);

    //유형, 기출만
    Page<Problem> findByTypeInAndPastIn(List<String> types, List<String> pasts, Pageable pageable);


  //난이도, 유형, 기출 모두
    Page<Problem> findByLevelInAndTypeInAndPastIn(List<String> levels, List<String> type, List<String> pasts, Pageable pageable);







}
