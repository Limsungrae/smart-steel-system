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

    // 사용자 입력 저장 서비스
    private final DemandInputService demandInputService;

    /**
     * ==========================================================
     * 계획 / 재고 입력 화면
     * URL : GET /plan
     * ==========================================================
     *
     * 사용자가 메뉴에서 "계획 / 재고 입력"을 클릭하면
     * HR / CR / GI 기본 데이터를 생성해서 화면으로 전달한다.
     */
    @GetMapping("/plan")
    public String inputForm(Model model) {

        DemandInputForm form = new DemandInputForm();

        List<DemandInput> defaultList = new ArrayList<>();

        // AI 예측 기준월
        String targetMonth = "2026-05";

        // ===========================
        // HR
        // ===========================
        DemandInput hr = new DemandInput();
        hr.setItemCode("HR");
        hr.setItemName("열연강판");
        hr.setTargetMonth(targetMonth);
        defaultList.add(hr);

        // ===========================
        // CR
        // ===========================
        DemandInput cr = new DemandInput();
        cr.setItemCode("CR");
        cr.setItemName("냉연강판");
        cr.setTargetMonth(targetMonth);
        defaultList.add(cr);

        // ===========================
        // GI
        // ===========================
        DemandInput gi = new DemandInput();
        gi.setItemCode("GI");
        gi.setItemName("아연도금강판");
        gi.setTargetMonth(targetMonth);
        defaultList.add(gi);

        // Form에 품목 리스트 저장
        form.setItemList(defaultList);

        // HTML로 전달
        model.addAttribute("inputForm", form);

        // templates/plan-inventory-input.html
        return "plan-inventory-input";
    }

    /**
     * ==========================================================
     * 검산 실행
     * URL : POST /plan/verify
     * ==========================================================
     *
     * 사용자가 입력한 생산계획/재고를 DB에 저장한 뒤
     * Python AI 모델을 실행한다.
     */
    @PostMapping("/plan/verify")
    public String verify(
            @ModelAttribute DemandInputForm inputForm
    ) {

        // ---------------------------------------
        // 사용자가 입력한 3개 품목 저장
        // ---------------------------------------
        demandInputService.saveAll(inputForm.getItemList());

        System.out.println("==================================");
        System.out.println("사용자 입력 DB 저장 완료");
        System.out.println("Python AI 실행");
        System.out.println("==================================");

        // ForecastController 실행
        return "redirect:/forecast/run";
    }

}