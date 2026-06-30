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

    /**
     * ==========================================================
     * 사용자가 입력한 운영 기준 데이터를 저장
     *
     * 현재 프로젝트는 한 번의 AI 검산 결과만 사용하므로
     * 기존 데이터를 모두 삭제한 뒤 새 데이터를 저장한다.
     * ==========================================================
     */
    @Transactional
    public void saveAll(List<DemandInput> itemList) {

        // 기존 입력 데이터 삭제
        demandInputRepository.deleteAll();

        // 새 입력 데이터 저장
        demandInputRepository.saveAll(itemList);

        System.out.println("===== DemandInput 저장 완료 =====");
    }

}