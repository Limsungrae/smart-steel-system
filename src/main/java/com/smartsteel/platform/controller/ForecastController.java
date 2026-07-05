package com.smartsteel.platform.controller;

import com.smartsteel.platform.service.PythonExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ForecastController {

    private final PythonExecutionService pythonExecutionService;

    @GetMapping("/forecast/run")
    public String runForecast() {

        System.out.println("========== Python AI 실행 ==========");

        String result = pythonExecutionService.runPythonModel();

        System.out.println(result);

        System.out.println("========== AI 종료 ==========");

        // AI 실행 끝나면 Dashboard 이동
        return "redirect:/dashboard";
    }

}