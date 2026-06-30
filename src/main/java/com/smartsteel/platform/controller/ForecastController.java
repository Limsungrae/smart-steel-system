package com.smartsteel.platform.controller;

import com.smartsteel.platform.service.PythonExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequiredArgsConstructor
public class ForecastController {

    private final PythonExecutionService pythonExecutionService;

    /**
     * AI 예측 실행
     */
    @GetMapping("/forecast/run")
    public String runForecast() {

        System.out.println("===== AI 예측 시작 =====");

        pythonExecutionService.runPythonModel();

        System.out.println("===== AI 예측 완료 =====");

        return "redirect:/dashboard";
    }

}