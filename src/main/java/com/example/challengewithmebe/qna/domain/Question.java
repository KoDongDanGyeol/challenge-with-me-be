package com.example.challengewithmebe.qna.domain;

import com.example.challengewithmebe.global.app.domain.BaseEntity;
import com.example.challengewithmebe.member.domain.Member;
import com.example.challengewithmebe.problem.domain.Problem;
import com.example.challengewithmebe.qna.dto.QuestionDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; // 질문 제목
    private String imgUrl; // 이미지 Url
    private String content; // 질문 내용

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member memberId; // 작성자 id

    // Problem 엔티티와의 관계 설정
    @ManyToOne(optional = false)
    @JoinColumn(name = "problem_id")
    private Problem problemId; // 문제

    // 관련 답변들
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    public void update(QuestionDTO questionDTO){
        this.title = questionDTO.getTitle();
        this.content = questionDTO.getContent();
        this.imgUrl = questionDTO.getImgUrl();
    }

}
