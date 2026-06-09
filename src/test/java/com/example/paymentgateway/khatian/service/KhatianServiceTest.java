package com.example.paymentgateway.khatian.service;

import com.example.paymentgateway.khatian.dto.KhatianResponseDTO;
import com.example.paymentgateway.khatian.entity.KhatianMaster;
import com.example.paymentgateway.khatian.exception.KhatianNotFoundException;
import com.example.paymentgateway.khatian.mapper.KhatianMapper;
import com.example.paymentgateway.khatian.repository.KhatianRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KhatianServiceTest {

    @Mock
    private KhatianRepository khatianRepository;

    @Spy
    private KhatianMapper khatianMapper;

    @InjectMocks
    private KhatianService khatianService;

    @Test
    void verifyByOwnerNameReturnsMatchingRecords() {
        KhatianMaster record = record();
        when(khatianRepository.findByOwnerNameContainingIgnoreCaseAndLgdVillageCode("ramesh", "922855"))
                .thenReturn(List.of(record));

        List<KhatianResponseDTO> response = khatianService.verifyByOwnerName("ramesh", "922855");

        assertThat(response).hasSize(1);
        assertThat(response.get(0).getKhatianNo()).isEqualTo("121");
        assertThat(response.get(0).getVillageCode()).isEqualTo("922855");
    }

    @Test
    void verifyByKhatianNoReturnsRecord() {
        when(khatianRepository.findByKhatianNoAndLgdVillageCode("121", "922855"))
                .thenReturn(Optional.of(record()));

        KhatianResponseDTO response = khatianService.verifyByKhatianNo("121", "922855");

        assertThat(response.getOwnerName()).isEqualTo("Ramesh Das");
        assertThat(response.getPlotNo()).isEqualTo("45");
    }

    @Test
    void verifyByPlotNoReturnsRecord() {
        when(khatianRepository.findByPlotNoAndLgdVillageCode("45", "922855"))
                .thenReturn(Optional.of(record()));

        KhatianResponseDTO response = khatianService.verifyByPlotNo("45", "922855");

        assertThat(response.getKhatianNo()).isEqualTo("121");
        assertThat(response.getOwnerName()).isEqualTo("Ramesh Das");
    }

    @Test
    void verifyByOwnerNameThrowsWhenNoRecordFound() {
        when(khatianRepository.findByOwnerNameContainingIgnoreCaseAndLgdVillageCode("missing", "922855"))
                .thenReturn(List.of());

        assertThatThrownBy(() -> khatianService.verifyByOwnerName("missing", "922855"))
                .isInstanceOf(KhatianNotFoundException.class)
                .hasMessage("Record not found");
    }

    private KhatianMaster record() {
        return KhatianMaster.builder()
                .khatianNo("121")
                .plotNo("45")
                .ownerName("Ramesh Das")
                .lgdVillageCode("922855")
                .build();
    }
}
