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

    @GetMapping("/plan")
    public String inputForm(Model model) {
        DemandInputForm form = new DemandInputForm();
        List<DemandInput> defaultList = new ArrayList<>();

        String targetMonth = "2026-05";

        defaultList.add(createDefaultInput("HR", "열연강판", targetMonth));
        defaultList.add(createDefaultInput("CR", "냉연강판", targetMonth));
        defaultList.add(createDefaultInput("GI", "아연도금강판", targetMonth));

        form.setItemList(defaultList);
        model.addAttribute("inputForm", form);

        return "plan-inventory-input";
    }

    @PostMapping("/plan/verify")
    public String verify(@ModelAttribute DemandInputForm inputForm) {
        demandInputService.saveAll(inputForm.getItemList());
        return "redirect:/forecast/run";
    }

    private DemandInput createDefaultInput(
            String itemCode,
            String itemName,
            String targetMonth
    ) {
        DemandInput input = new DemandInput();
        input.setItemCode(itemCode);
        input.setItemName(itemName);
        input.setTargetMonth(targetMonth);
        return input;
    }
}
