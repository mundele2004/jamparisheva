package com.example.paymentgateway.khatian.service;

import com.example.paymentgateway.khatian.dto.KhatianResponseDTO;
import com.example.paymentgateway.khatian.entity.KhatianMaster;
import com.example.paymentgateway.khatian.exception.KhatianNotFoundException;
import com.example.paymentgateway.khatian.mapper.KhatianMapper;
import com.example.paymentgateway.khatian.repository.KhatianRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class KhatianService {

    private final KhatianRepository khatianRepository;
    private final KhatianMapper khatianMapper;

    public List<KhatianResponseDTO> verifyByOwnerName(String ownerName, String villageCode) {
        log.info("Query executed for owner name search. villageCode={}", villageCode);
        List<KhatianMaster> records = khatianRepository
                .findByOwnerNameContainingIgnoreCaseAndLgdVillageCode(ownerName, villageCode);

        if (records.isEmpty()) {
            log.info("No khatian records found for owner name search. villageCode={}", villageCode);
            throw new KhatianNotFoundException("Record not found");
        }

        log.info("Records found for owner name search. count={}", records.size());
        return records.stream().map(khatianMapper::toResponse).toList();
    }

    public KhatianResponseDTO verifyByKhatianNo(String khatianNo, String villageCode) {
        log.info("Query executed for khatian number search. villageCode={}", villageCode);
        KhatianMaster record = khatianRepository.findByKhatianNoAndLgdVillageCode(khatianNo, villageCode)
                .orElseThrow(() -> new KhatianNotFoundException("Record not found"));

        log.info("Khatian record found. khatianNo={}, villageCode={}", khatianNo, villageCode);
        return khatianMapper.toResponse(record);
    }

    public KhatianResponseDTO verifyByPlotNo(String plotNo, String villageCode) {
        log.info("Query executed for plot number search. villageCode={}", villageCode);
        KhatianMaster record = khatianRepository.findByPlotNoAndLgdVillageCode(plotNo, villageCode)
                .orElseThrow(() -> new KhatianNotFoundException("Record not found"));

        log.info("Plot record found. plotNo={}, villageCode={}", plotNo, villageCode);
        return khatianMapper.toResponse(record);
    }
}
