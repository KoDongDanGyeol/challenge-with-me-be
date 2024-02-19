package com.example.challengewithmebe.problem.service;


import com.example.challengewithmebe.ide.model.ParamDto;
import com.example.challengewithmebe.ide.model.RunResult;
import com.example.challengewithmebe.ide.model.SubmitResult;
import com.example.challengewithmebe.ide.model.TestcaseDto;
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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestcaseService {

    private final TestcaseRepository testcaseRepository;
    private final CompileBuilder compileBuilder;

    // 코드 실행
    public List<RunResult> run(Long problemId, String inputCode) throws Exception {
        List<RunResult> returnList = new ArrayList<>();

        Object obj = compileBuilder.compileCode(inputCode);
        if (obj instanceof String) {
            returnList.add(new RunResult(obj.toString()));
            return returnList;
        }

        List<Testcase> testCases = testcaseRepository.findByProblemIdAndIsHiddenIsFalse(problemId);
        for (Testcase testCase : testCases) {
            boolean passed = false;
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

                    case "int[]" -> {
                        methodParamClass[i] = int[].class;
                        methodParamObject[i++] = new Gson().fromJson(_input[1], int[].class);
                    }

                    case "int[][]" -> {
                        methodParamClass[i] = int[][].class;
                        methodParamObject[i++] = new Gson().fromJson(_input[1], int[][].class);
                    }

                    case "long" -> {
                        methodParamClass[i] = long.class;
                        methodParamObject[i++] = Long.parseLong(_input[1]);
                    }

                }
                resultDtoInput[i-1] = _input[1];
            }

            Method method = obj.getClass().getMethod("solution", methodParamClass);
            Object result = method.invoke(obj, methodParamObject);
            long afterTime = System.currentTimeMillis();

            String[] outputTypeAndValue = testCase.getOutputData().split(":");
            if (result.toString().equals(outputTypeAndValue[1])) {
                rr = "테스트를 통과하였습니다.";
                passed = true;
            } else {
                rr = String.format("실행한 결괏값 %s이 기댓값 %s과 다릅니다.", result, testCase.getOutputData());
            }

            returnList.add(new RunResult(resultDtoInput, testCase.getOutputData(), rr, (afterTime - beforeTime), passed));
        }


        return returnList;

    }

    public List<SubmitResult> submit(Long problemId, String inputCode) throws Exception {
        List<SubmitResult> returnList = new ArrayList<>();

        Object obj = compileBuilder.compileCode(inputCode);
        if (obj instanceof String) {
            returnList.add(new SubmitResult("컴파일 에러", null, false));
            return returnList;
        }

        List<Testcase> testCases = testcaseRepository.findByProblemIdAndIsHiddenIsTrue(problemId);
        for (Testcase testCase : testCases) {
            boolean passed = false;
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

                    case "int[]" -> {
                        methodParamClass[i] = int[].class;
                        methodParamObject[i++] = new Gson().fromJson(_input[1], int[].class);
                    }

                    case "int[][]" -> {
                        methodParamClass[i] = int[][].class;
                        methodParamObject[i++] = new Gson().fromJson(_input[1], int[][].class);
                    }

                    case "long" -> {
                        methodParamClass[i] = long.class;
                        methodParamObject[i++] = Long.parseLong(_input[1]);
                    }

                }
                resultDtoInput[i-1] = _input[1];
            }

            Method method = obj.getClass().getMethod("solution", methodParamClass);
            Object result = method.invoke(obj, methodParamObject);
            long afterTime = System.currentTimeMillis();

            String[] outputTypeAndValue = testCase.getOutputData().split(":");
            if (result.toString().equals(outputTypeAndValue[1])) {
                rr = String.format("통과 (수행시간 : %s)", (afterTime - beforeTime));
                passed = true;
            } else {
                rr = String.format("실패 (수행시간 : %s)", (afterTime - beforeTime));
            }

            returnList.add(new SubmitResult(null, rr, passed));
        }
        return returnList;

    }

    public ParamDto getParamTypesAndTestcases(Long problemId) {
        List<Testcase> testCases = testcaseRepository.findByProblemIdAndIsHiddenIsFalse(problemId);

        TestcaseDto testcaseDto = new TestcaseDto();
        List<TestcaseDto> testcaseValues = new ArrayList<>();

        for (Testcase testCase : testCases) {
            String[] testcaseValueAndType = testCase.getOutputData().split(":");
            String[] inputs = testCase.getInputData().split("#");

            List<String> testcaseType = new ArrayList<>();
            List<String> testcaseValue = new ArrayList<>();

            for (String input : inputs) {
                String[] _input = input.split(":");
                testcaseType.add(_input[0]);
                testcaseValue.add(_input[1]);
            }

            if (testcaseDto.getInput() == null) {
                testcaseDto.setInput(testcaseType);
                testcaseDto.setExpected(testcaseValueAndType[0]);
            }


            testcaseValues.add(new TestcaseDto(testcaseValue,testcaseValueAndType[1]));

        }


        return new ParamDto(testcaseDto, testcaseValues);
    }

}
