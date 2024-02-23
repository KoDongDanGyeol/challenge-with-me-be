package com.example.challengewithmebe.qna.service;

import com.example.challengewithmebe.global.exception.auth.OwnerOnlyOperationException;
import com.example.challengewithmebe.global.exception.notExist.NotExistAnswerException;
import com.example.challengewithmebe.global.exception.notExist.NotExistChallengeException;
import com.example.challengewithmebe.global.exception.notExist.NotExistMemberException;
import com.example.challengewithmebe.global.exception.notExist.NotExistQuestionException;
import com.example.challengewithmebe.member.domain.Member;
import com.example.challengewithmebe.member.dto.MemberDTO;
import com.example.challengewithmebe.member.repository.MemberRepository;
import com.example.challengewithmebe.member.service.MemberService;
import com.example.challengewithmebe.problem.domain.Problem;
import com.example.challengewithmebe.problem.repository.ProblemRepository;
import com.example.challengewithmebe.qna.domain.Answer;
import com.example.challengewithmebe.qna.domain.Question;
import com.example.challengewithmebe.qna.dto.AnswerDTO;
import com.example.challengewithmebe.qna.dto.QuestionDTO;
import com.example.challengewithmebe.qna.dto.QuestionPreviewDTO;
import com.example.challengewithmebe.qna.repository.AnswerRepository;
import com.example.challengewithmebe.qna.repository.QuestionRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class QnAService {

    private final MemberService memberService;
    private final EntityManager entityManager;
    private final MemberRepository memberRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ProblemRepository problemRepository;

    // 모든 질문 return
    public List<QuestionDTO> allQuestions() {
        List<QuestionDTO> questionDTO =
                convertToQuestionDTOs(questionRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedAt")));
        return questionDTO;
    }

    // 한 개의 질문과 그에 대한 답변들
    public QuestionDTO findOneQuestion(Long questionId) {
        Question q = questionRepository.findById(questionId).orElseThrow(NotExistQuestionException::new);
        QuestionDTO qDTO = convertToQuestionDTO(q);
        return qDTO;
    }

    // 특정 문제에 대한 질문 return
    public List<QuestionDTO> questionForOneProblem(Long problemId) {
        List<QuestionDTO> questionDTO =
                convertToQuestionDTOs(questionRepository.findByProblemId_IdOrderByModifiedAtDesc(problemId));
            return questionDTO;
    }

    // 질문 추가
    public Long addQuestion(QuestionDTO question, Long memberId) {
        question.setMemberId(memberId);
        Question q = questionRepository.save(convertToQuestion(question));
        return q.getId();
    }

    // 특정 질문에 대한 답변들 return
    public List<AnswerDTO> answersForOneQuestion(Long questionId) {
        List<AnswerDTO> dtos = new ArrayList<>();
        List<Answer> answers = answerRepository.findByQuestionId(questionId);
        for(Answer a : answers){
            dtos.add(convertToAnswerDTO(a));
        }
        return dtos;
    }

    // 답변 작성
    public Long addAnswer(AnswerDTO answerDTO, Long memberId) {
        answerDTO.setMemberId(memberId);
        return answerRepository.save(convertToAnswer(answerDTO)).getId();
    }

    // 질문 수정
    public Long updateQuestion(QuestionDTO questionDTO, Long memberId){
        Question q = questionRepository.findById(questionDTO.getId()).orElseThrow(NotExistQuestionException::new);
        if(q.getMemberId().getId() != memberId){
            throw new OwnerOnlyOperationException();
        }
        q.update(questionDTO);
        return questionRepository.save(q).getId();
    }

    // 답변 수정
    public Long updateAnswer(AnswerDTO answerDTO, Long memberId){
        Answer a = answerRepository.findById(answerDTO.getId()).orElseThrow(NotExistAnswerException::new);
        if(a.getMemberId().getId() != memberId){
            throw new OwnerOnlyOperationException();
        }
        a.update(answerDTO);
        return answerRepository.save(a).getId();
    }

    // 질문 삭제
    public boolean deleteOneQuestion(Long questionId,Long memberId) {
        Question q = questionRepository.findById(questionId).orElseThrow(NotExistQuestionException::new);
        if(q.getMemberId().getId() != memberId){
            throw new OwnerOnlyOperationException();
        }
        questionRepository.deleteById(questionId);
        return true;
    }

    // 답변 삭제
    public boolean deleteOneAnswer(Long answerId, Long memberId) {
        Answer a = answerRepository.findById(answerId).orElseThrow(NotExistAnswerException::new);
        if(a.getMemberId().getId() != memberId){
            throw new OwnerOnlyOperationException();
        }
        answerRepository.deleteById(answerId);
        return true;
    }

    // 모든 질문에 대한 미리보기 return
    public Page<QuestionPreviewDTO> findAllQuestionPreviews(int page, int size, String type, Long memberId){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "modifiedAt"));
        Page<Question> questions;
        if(type.equals("my")){
            questions = questionRepository.findByMemberId_Id(memberId, pageable);
        }else{
            questions = questionRepository.findAll(pageable);
        }

        Page<QuestionPreviewDTO> previewDTOS = questions.map(this::convertFromQuestionToQuestionPreviewDTO);
        return previewDTOS;
    }

    // 특정 문제에 대한 질문 미리보기 return
    public Page<QuestionPreviewDTO> findQuestionPreviewsForSpecificProblem(Long problemId,int page, int size, String type, Long memberId){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "modifiedAt"));
        Page<Question> questions;
        if(type.equals("my")){
            questions = questionRepository.findByProblemId_IdAndMemberId_Id(problemId, memberId, pageable);
        }else {
            questions = questionRepository.findByProblemId_Id(problemId, pageable);
        }

        Page<QuestionPreviewDTO> previewDTOS = questions.map(this::convertFromQuestionToQuestionPreviewDTO);
        return previewDTOS;
    }

    private List<QuestionDTO> convertToQuestionDTOs(List<Question> questions) {
        List<QuestionDTO> dtos = new ArrayList<>();
        for(Question q : questions){
            dtos.add(convertToQuestionDTO(q));
        }
        return dtos;
    }

    private QuestionDTO convertToQuestionDTO(Question question) {
        List<AnswerDTO> answerDTOs = question.getAnswers().stream()
                .map(this::convertToAnswerDTO) // 여기를 수정했습니다
                .collect(Collectors.toList());

        String[] questionMemberInfo = memberCheck(() -> Optional.ofNullable(question.getMemberId()));

        Long memberId = Optional.ofNullable(questionMemberInfo[0])
                .map(Long::valueOf)
                .orElse(null);

        String[] problemInfo = problemCheck(Optional.ofNullable(question.getProblemId()));

        Long problemId = Optional.ofNullable(problemInfo[0])
                .map(Long::valueOf)
                .orElse(null);

        return QuestionDTO.builder()
                .id(question.getId())
                .problemId(problemId)
                .problemTitle(problemInfo[1])
                .title(question.getTitle())
                .imgUrl(question.getImgUrl())
                .content(question.getContent())
                .name(questionMemberInfo[1])
                .profileImgUrl(questionMemberInfo[2])
                .memberId(memberId)
                .createdAt(question.getCreatedAt())
                .modifiedAt(question.getModifiedAt())
                .answerCounts(answerDTOs.size())
                .answers(answerDTOs)
                .build();
    }


    private AnswerDTO convertToAnswerDTO(Answer answer){
        String[] answerMemberInfo = memberCheck(() -> Optional.ofNullable(answer.getMemberId()));

        Long memberId = Optional.ofNullable(answerMemberInfo[0])
                .map(Long::valueOf)
                .orElse(null);

        return AnswerDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .memberId(memberId)
                .name(answerMemberInfo[1])
                .profileImgUrl(answerMemberInfo[2])
                .questionId(answer.getQuestion().getId())
                .createdAt(answer.getCreatedAt())
                .modifiedAt(answer.getModifiedAt())
                .build();

    }

    private Question convertToQuestion(QuestionDTO question){
        Member member = memberRepository.findById(question.getMemberId())
                .orElseThrow(NotExistMemberException::new);
        Optional<Member> memberOptional = memberRepository.findById(question.getMemberId());
        Problem problem = problemRepository.findById(question.getProblemId())
                .orElseThrow(NotExistChallengeException::new);
        return Question.builder()
                .problemId(problem)
                .title(question.getTitle())
                .imgUrl(question.getImgUrl())
                .content(question.getContent())
                .memberId(member)
                .build();
    }

    // 사용자가 입력한 답변을 DB에 저장 하기 위해 Answer Entity 형식으로 바꾸는 메서드
    private Answer convertToAnswer(AnswerDTO answer){
        Member memberRef = entityManager.getReference(Member.class, answer.getMemberId());
        Question questionRef = entityManager.getReference(Question.class, answer.getQuestionId());

        return Answer.builder()
                .content(answer.getContent())
                .memberId(memberRef)
                .question(questionRef)
                .build();
    }

    // 받아온 questionDTO를 questionpreviewDTO로 변환
    private QuestionPreviewDTO convertToQuestionPreviewDTO(QuestionDTO questionDTO){
        return QuestionPreviewDTO.builder()
                .id(questionDTO.getId())
                .problemId(questionDTO.getProblemId())
                .problemTitle(questionDTO.getProblemTitle())
                .title(questionDTO.getTitle())
                .memberId(questionDTO.getMemberId())
                .answerCounts(questionDTO.getAnswerCounts())
                .name(questionDTO.getName())
                .profileImgUrl(questionDTO.getProfileImgUrl())
                .createdAt(questionDTO.getCreatedAt())
                .modifiedAt(questionDTO.getModifiedAt())
                .build();
    }

    private QuestionPreviewDTO convertFromQuestionToQuestionPreviewDTO(Question question){
        QuestionDTO q = convertToQuestionDTO(question);
        return convertToQuestionPreviewDTO(q);
    }

    private MemberDTO getMemberInfo(Long memberId){
        return memberService.getInfo(memberId);
    }

    // MemberId를 확인하고 null이 아니면 사용자의 정보를, null이면 default를 return
    private String[] memberCheck(Supplier<Optional<Member>> memberSupplier) {
        String[] memberInfo = new String[3];
        Optional<Member> memberOptional = memberSupplier.get(); // Supplier로부터 Optional<Member>를 받아옵니다.

        if (!memberOptional.isPresent()) {
            // 멤버가 탈퇴한 경우 처리
            memberInfo[0] = null;
            memberInfo[1] = "탈퇴한 사용자";
            memberInfo[2] = "https://default_image.jpg"; // 프로필 이미지 정보가 없으므로 기본 이미지 URL 설정
        } else {
            // 멤버가 탈퇴하지 않은 경우
            Member member = memberOptional.get(); // Optional<Member>에서 Member 인스턴스를 추출
            memberInfo[0] = String.valueOf(member.getId());
            memberInfo[1] = member.getName();
            memberInfo[2] = member.getImgUrl();
        }
        return memberInfo;
    }

    private String[] problemCheck(Optional<Problem> problemOptional) {
        String[] problemInfo = new String[2];

        if(problemOptional.isEmpty()){
            // 문제가 삭제된 경우
            problemInfo[0] = null;
            problemInfo[1] = "존재하지 않는 문제입니다.";
            return problemInfo;
        }

        Problem p = problemOptional.get();
        problemInfo[0] = String.valueOf(p.getId());
        problemInfo[1] = p.getTitle();
        return problemInfo;


    }

}
