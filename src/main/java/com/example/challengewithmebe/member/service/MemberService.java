package com.example.challengewithmebe.member.service;

import com.example.challengewithmebe.chat.repository.ChatRoomRepository;
import com.example.challengewithmebe.global.exception.notExist.NotExistMemberException;
import com.example.challengewithmebe.member.domain.Member;
import com.example.challengewithmebe.member.dto.MemberDTO;
import com.example.challengewithmebe.member.repository.MemberRepository;
import com.example.challengewithmebe.qna.repository.AnswerRepository;
import com.example.challengewithmebe.qna.repository.QuestionRepository;
import com.example.challengewithmebe.solution.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SolutionRepository solutionRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public MemberDTO getInfo(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(NotExistMemberException::new);
        MemberDTO response = MemberDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .imgUrl(member.getImgUrl())
                .provider(member.getProvider())
                .build();

        return response;
    }

    @Transactional
    public MemberDTO editName(String name, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(NotExistMemberException::new);
        member.editName(name);
        member = memberRepository.save(member);

        MemberDTO response = MemberDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .imgUrl(member.getImgUrl())
                .provider(member.getProvider())
                .build();

        return response;
    }

    @Transactional
    public void withdrawal(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(NotExistMemberException::new);
        chatRoomRepository.deleteAllByMemberId(memberId); //채팅 삭제
        solutionRepository.updateMemberIdToNull(memberId); //풀이 memberid null로 변경
        memberRepository.delete(member); //유저 삭제
    }
}
