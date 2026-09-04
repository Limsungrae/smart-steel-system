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
        pythonExecutionService.runPythonModel();
        return "redirect:/dashboard";
    }
}
