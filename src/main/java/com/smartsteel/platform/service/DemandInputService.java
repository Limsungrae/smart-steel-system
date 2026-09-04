package com.smartsteel.platform.service;

import com.smartsteel.platform.entity.DemandInput;
import com.smartsteel.platform.repository.DemandInputRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DemandInputService {

    private final DemandInputRepository demandInputRepository;

    @Transactional
    public void saveAll(List<DemandInput> itemList) {
        demandInputRepository.deleteAll();
        demandInputRepository.saveAll(itemList);
    }
}
