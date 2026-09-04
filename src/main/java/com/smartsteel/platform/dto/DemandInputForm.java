package com.smartsteel.platform.dto;

import com.smartsteel.platform.entity.DemandInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DemandInputForm {

    private List<DemandInput> itemList = new ArrayList<>();
}
