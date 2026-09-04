package com.smartsteel.platform.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class PythonExecutionService {

    public String runPythonModel() {
        StringBuilder output = new StringBuilder();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", "main.py");
            processBuilder.directory(new File("python"));
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                log.warn("Python forecast process exited with code {}", exitCode);
            }
        } catch (Exception e) {
            log.error("Failed to execute Python forecast process", e);
            return "{\"error\": \"파이썬 실행 실패\"}";
        }

        return output.toString();
    }
}
