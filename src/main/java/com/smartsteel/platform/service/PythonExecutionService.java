package com.smartsteel.platform.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.File; // ⚡ 추가
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class PythonExecutionService {

    public String runPythonModel() {
        StringBuilder output = new StringBuilder();
        try {
            // 1. 실행 명령어를 수정합니다.
            // 작업 디렉토리를 파이썬 폴더 안으로 바꿀 것이므로, 여기서는 그냥 "main.py"만 실행하면 됩니다.
            ProcessBuilder processBuilder = new ProcessBuilder("python", "main.py");

            // ⚡ [핵심 추가] 파이썬 스크립트가 실행되는 '기준 위치'를 python 폴더 내부로 강제 지정합니다.
            processBuilder.directory(new File("python"));

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

// 기존의 reader 부분을 아래 코드로 교체해 줍니다.
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), java.nio.charset.StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            System.out.println("파이썬 프로세스 종료 코드: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"파이썬 실행 실패\"}";
        }

        return output.toString();
    }
}