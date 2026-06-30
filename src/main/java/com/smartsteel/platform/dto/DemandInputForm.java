package com.smartsteel.platform.dto;

import com.smartsteel.platform.entity.DemandInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * ==========================================================
 * 사용자 입력 화면(Form) 전체를 담는 DTO
 *
 * 화면에서는 HR / CR / GI 3개의 품목을
 * 한번에 전송하므로 List 형태로 받는다.
 * ==========================================================
 */
@Getter
@Setter
public class DemandInputForm {

    /**
     * 화면에서 입력한 품목 목록
     */
    private List<DemandInput> itemList = new ArrayList<>();

}