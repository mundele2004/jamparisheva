package com.example.paymentgateway.khatian.controller;

import com.example.paymentgateway.khatian.dto.ApiResponseDTO;
import com.example.paymentgateway.khatian.dto.KhatianResponseDTO;
import com.example.paymentgateway.khatian.dto.VerifyRequestDTO;
import com.example.paymentgateway.khatian.service.KhatianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/khatian_services")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Khatian Verification", description = "ROR verification APIs by owner, khatian number, and plot number")
public class KhatianController {

    private static final String OWNER = "owner";
    private static final String KHATIAN = "khatian";
    private static final String PLOT = "plot";

    private final KhatianService khatianService;

    @PostMapping("/verify")
    @Operation(summary = "Verify ROR records", description = "Search by owner name, khatian number, or plot number using search_key.")
    public ResponseEntity<ApiResponseDTO<?>> verify(@Valid @RequestBody VerifyRequestDTO request) {
        log.info("Khatian verification request received. searchKey={}, villageCode={}",
                request.getSearchKey(), request.getLgdVillageCode());

        String searchKey = request.getSearchKey().trim().toLowerCase();
        String searchValue = request.getSearchValue().trim();
        String villageCode = request.getLgdVillageCode().trim();

        return switch (searchKey) {
            case OWNER -> {
                List<KhatianResponseDTO> response = khatianService.verifyByOwnerName(searchValue, villageCode);
                yield ResponseEntity.ok(ApiResponseDTO.success("Records found", response));
            }
            case KHATIAN -> {
                KhatianResponseDTO response = khatianService.verifyByKhatianNo(searchValue, villageCode);
                yield ResponseEntity.ok(ApiResponseDTO.success("Khatian found", response));
            }
            case PLOT -> {
                KhatianResponseDTO response = khatianService.verifyByPlotNo(searchValue, villageCode);
                yield ResponseEntity.ok(ApiResponseDTO.success("Plot found", response));
            }
            default -> throw new IllegalArgumentException("search_key must be one of owner, khatian, or plot");
        };
    }
}
