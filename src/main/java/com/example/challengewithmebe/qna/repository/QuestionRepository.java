package com.example.challengewithmebe.qna.repository;

import com.example.challengewithmebe.qna.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByProblemIdOrderByModifiedAtDesc(Long problemId);

    // 특정 문제에 대한 질문들 페이지로 넘김
    Page<Question> findByProblemId(Long problemId, Pageable pageable);

}