package com.example.challengewithmebe.problem.service;


import com.example.challengewithmebe.ide.model.RunResult;
import com.example.challengewithmebe.ide.model.SubmitResult;
import com.example.challengewithmebe.ide.util.CompileBuilder;
import com.example.challengewithmebe.problem.domain.Testcase;
import com.example.challengewithmebe.problem.repository.TestcaseRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestcaseService {

    private final TestcaseRepository testcaseRepository;
    private final CompileBuilder compileBuilder;

    // {problemId}에 대한 공개 테스트 케이스만 가져온다.
    public List<Testcase> findOpenTestcasesById(Long problemId) {
        return testcaseRepository.findAll().stream()
                .filter(tc -> tc.getProblem().getId().equals(problemId))
                .filter(tc -> tc.getIsHidden().equals(false))
                .collect(Collectors.toList());
    }

    // {problemId}에 대한 히든 테스트 케이스만 가져온다.
    public List<Testcase> findHiddenTestcasesById(Long problemId) {
        return testcaseRepository.findAll().stream()
                .filter(tc -> tc.getProblem().getId().equals(problemId))
                .filter(tc -> tc.getIsHidden().equals(true))
                .collect(Collectors.toList());
    }

    // 코드 실행
    public List<RunResult> run(Long problemId, String inputCode) throws Exception {
        List<RunResult> returnList = new ArrayList<>();

        Object obj = compileBuilder.compileCode(inputCode);
        if (obj instanceof String) {
            returnList.add(new RunResult(obj.toString()));
            return returnList;
        }

        List<Testcase> testCases = findOpenTestcasesById(problemId);
        for (Testcase testCase : testCases) {

            long beforeTime = System.currentTimeMillis();
            String[] inputs = testCase.getInputData().split("#");
            String[] resultDtoInput = new String[inputs.length];
            Class[] methodParamClass = new Class[inputs.length];
            Object[] methodParamObject = new Object[inputs.length];

            int i = 0;
            String rr;

            for (String input : inputs) {
                String[] _input = input.split(":");
                switch (_input[0]) {
                    case "int" -> {
                        methodParamClass[i] = int.class;
                        methodParamObject[i++] = Integer.parseInt(_input[1]);
                    }

                    case "String" -> {
                        methodParamClass[i] = String.class;
                        methodParamObject[i++] = _input[1];
                    }

                    case "List" -> {
                        methodParamClass[i] = int[].class;
                        methodParamObject[i++] = new Gson().fromJson(_input[1], int[].class);
                    }

                }
                resultDtoInput[i-1] = _input[1];
            }

            Method method = obj.getClass().getMethod("solution", methodParamClass);
            Object result = method.invoke(obj, methodParamObject);
            long afterTime = System.currentTimeMillis();

            if (result.toString().equals(testCase.getOutputData())) {
                rr = "테스트를 통과하였습니다.";
            } else {
                rr = String.format("실행한 결괏값 %s이 기댓값 %s과 다릅니다.", result, testCase.getOutputData());
            }

            // t[1]: 입력값, output: 기댓값, result: 결과값
            returnList.add(new RunResult(resultDtoInput, testCase.getOutputData(), rr, (afterTime - beforeTime)));
        }


        return returnList;

    }

    public List<SubmitResult> submit(Long problemId, String inputCode) throws Exception {
        List<SubmitResult> returnList = new ArrayList<>();

        Object obj = compileBuilder.compileCode(inputCode);
        if (obj instanceof String) {
            returnList.add(new SubmitResult("컴파일 에러", null));
            return returnList;
        }

        List<Testcase> testCases = findHiddenTestcasesById(problemId);
        for (Testcase testCase : testCases) {

            long beforeTime = System.currentTimeMillis();
            String[] inputs = testCase.getInputData().split("#");
            String[] resultDtoInput = new String[inputs.length];
            Class[] methodParamClass = new Class[inputs.length];
            Object[] methodParamObject = new Object[inputs.length];

            int i = 0;
            // int successCount = 0;
            String rr;

            for (String input : inputs) {
                String[] _input = input.split(":");
                switch (_input[0]) {
                    case "int" -> {
                        methodParamClass[i] = int.class;
                        methodParamObject[i++] = Integer.parseInt(_input[1]);
                    }

                    case "String" -> {
                        methodParamClass[i] = String.class;
                        methodParamObject[i++] = _input[1];
                    }

                    case "List" -> {
                        methodParamClass[i] = int[].class;
                        methodParamObject[i++] = new Gson().fromJson(_input[1], int[].class);
                    }

                }
                resultDtoInput[i-1] = _input[1];
            }

            Method method = obj.getClass().getMethod("solution", methodParamClass);
            Object result = method.invoke(obj, methodParamObject);
            long afterTime = System.currentTimeMillis();

            if (result.toString().equals(testCase.getOutputData())) {
                rr = String.format("통과 (수행시간 : %s)", (afterTime - beforeTime));
                // successCount++;
            } else {
                rr = String.format("실패 (수행시간 : %s)", (afterTime - beforeTime));
            }

            returnList.add(new SubmitResult(null, rr));
        }
        return returnList;

    }

}
