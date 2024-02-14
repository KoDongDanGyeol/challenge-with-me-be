package com.example.challengewithmebe.solution.repository;

import com.example.challengewithmebe.submit.domain.Submit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SolutionRepository extends JpaRepository<Submit, Long> {
    @Query("SELECT s, m.name, m.imgUrl " +
            "FROM Submit s, Member m " +
            "WHERE s.memberId = m.id AND s.isCorrect = true")
    List<Object[]> findByIsCorrectTrue();

    @Query("SELECT s, m.name, m.imgUrl " +
            "FROM Submit s, Member m " +
            "WHERE s.memberId = m.id AND s.isCorrect = true AND s.problemId = :problemId")
    List<Object[]> findByIsCorrectTrueAndProblemId(@Param("problemId") Long problemId);

    Long countByIsCorrectTrue();


    @Query("SELECT s, m.name, m.imgUrl " +
            "FROM Submit s, Member m " +
            "WHERE s.memberId = m.id AND s.isCorrect = true AND s.language = :language AND s.memberId = :memberId AND s.problemId = :problemId")
    Page<Object[]> findByIsCorrectTrueAndLanguageAndMemberIdAndProblemId(@Param("language")String language,
                                                                         @Param("memberId")Long memberId,
                                                                         @Param("problemId")Long problemId,
                                                                       Pageable pageable);

    @Query("SELECT s, m.name, m.imgUrl " +
            "FROM Submit s, Member m " +
            "WHERE s.memberId = m.id AND s.isCorrect = true AND s.language = :language AND s.problemId = :problemId")
    Page<Object[]> findByIsCorrectTrueAndLanguageAndProblemId(@Param("language") String language,
                                                              @Param("problemId") Long problemId,
                                                            Pageable pageable);
}
