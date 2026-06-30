package com.smartsteel.platform.controller;

import com.smartsteel.platform.dto.DemandInputForm;
import com.smartsteel.platform.entity.DemandInput;
import com.smartsteel.platform.service.DemandInputService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DemandInputController {

    private final DemandInputService demandInputService;

    /**
     * ==========================================================
     * 1. 계획 / 재고 입력 화면 표시 (GET)
     * ==========================================================
     * 사용자가 /demand-input 주소로 진입할 때 호출됩니다.
     * DB의 NOT NULL 제약조건을 만족하도록 품목명과 예측 기준 월을 미리 세팅하여 화면에 넘깁니다.
     */
    @GetMapping("/demand-input")
    public String inputForm(Model model) {
        DemandInputForm form = new DemandInputForm();
        List<DemandInput> defaultList = new ArrayList<>();

        // 시스템 및 AI 모델의 분석 기준 월인 "2026-05" 지정
        String currentTargetMonth = "2026-05";

        // 💡 [HR] 열연강판 초기화
        DemandInput hr = new DemandInput();
        hr.setItemCode("HR");
        hr.setItemName("열연강판");
        hr.setTargetMonth(currentTargetMonth);
        defaultList.add(hr);

        // 💡 [CR] 냉연강판 초기화
        DemandInput cr = new DemandInput();
        cr.setItemCode("CR");
        cr.setItemName("냉연강판");
        cr.setTargetMonth(currentTargetMonth);
        defaultList.add(cr);

        // 💡 [GI] 아연도금강판 초기화
        DemandInput gi = new DemandInput();
        gi.setItemCode("GI");
        gi.setItemName("아연도금강판");
        gi.setTargetMonth(currentTargetMonth);
        defaultList.add(gi);

        form.setItemList(defaultList);
        model.addAttribute("inputForm", form);

        return "plan-inventory-input";
    }

    /**
     * ==========================================================
     * 2. 생산계획 / 재고 / 시장점유율 저장 후 AI 파이프라인 가동 (POST)
     * ==========================================================
     * 화면에서 [검산 실행] 버튼을 누르면 호출됩니다.
     * 사용자가 입력한 실시간 데이터를 DB에 저장한 뒤, AI 예측 엔진 주소로 리다이렉트합니다.
     */
    @PostMapping("/demand-input/save")
    public String save(@ModelAttribute DemandInputForm inputForm) {
        // 1. 화면에서 넘어온 품목별 리스트 전체를 DB(demand_input 테이블)에 일괄 저장
        demandInputService.saveAll(inputForm.getItemList());
        System.out.println("===== [Spring] 1단계: 사용자 실시간 입력 데이터 DB 저장 완료 =====");

        // 2. 저장 완료 후 AI 예측 실행 컨트롤러 주소로 바톤 터치 (리다이렉트)
        // 이 주소 내부에서 ProcessBuilder 등을 통해 파이썬 main.py를 자동으로 실행하게 됩니다.
        return "redirect:/forecast/run";
    }
}