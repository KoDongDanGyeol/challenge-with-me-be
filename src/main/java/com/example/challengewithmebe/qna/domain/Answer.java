package com.example.challengewithmebe.qna.domain;

import com.example.challengewithmebe.global.app.domain.BaseEntity;
import com.example.challengewithmebe.member.domain.Member;
import com.example.challengewithmebe.qna.dto.AnswerDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content; // 내용

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member memberId; // 작성자 id

    // 질문의 id
    // Foreign Key
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public void update(AnswerDTO answerDTO){
        this.content = answerDTO.getContent();
    }
}
