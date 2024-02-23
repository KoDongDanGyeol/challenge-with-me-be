package com.example.challengewithmebe.qna.repository;

import com.example.challengewithmebe.qna.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByProblemId_IdOrderByModifiedAtDesc(Long problemId);

    // 특정 문제에 대한 질문들 페이지로 넘김
    Page<Question> findByProblemId_Id(Long problemId, Pageable pageable);

    // 특정 문제에 대한 질문 중 특정 사용자가 작성한 질문들만 페이지로 넘김
    Page<Question> findByProblemId_IdAndMemberId_Id(Long problemId,Long memberId, Pageable pageable);

    // 모든 질문 중 특정 사용자가 작성한 질문들만 페이지로 넘김
    Page<Question> findByMemberId_Id(Long MemberId,Pageable pageable);

}