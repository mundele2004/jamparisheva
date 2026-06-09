package com.example.paymentgateway.khatian.mapper;

import com.example.paymentgateway.khatian.dto.KhatianResponseDTO;
import com.example.paymentgateway.khatian.entity.KhatianMaster;
import org.springframework.stereotype.Component;

@Component
public class KhatianMapper {

    public KhatianResponseDTO toResponse(KhatianMaster khatianMaster) {
        return KhatianResponseDTO.builder()
                .khatianNo(khatianMaster.getKhatianNo())
                .ownerName(khatianMaster.getOwnerName())
                .plotNo(khatianMaster.getPlotNo())
                .villageCode(khatianMaster.getLgdVillageCode())
                .build();
    }
}
