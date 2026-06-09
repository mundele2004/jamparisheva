package com.example.paymentgateway.khatian.controller;

import com.example.paymentgateway.khatian.dto.KhatianResponseDTO;
import com.example.paymentgateway.khatian.exception.KhatianExceptionHandler;
import com.example.paymentgateway.khatian.exception.KhatianNotFoundException;
import com.example.paymentgateway.khatian.service.KhatianService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KhatianController.class)
@Import(KhatianExceptionHandler.class)
class KhatianControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private KhatianService khatianService;

    @Test
    void verifySearchesByOwner() throws Exception {
        KhatianResponseDTO record = record();
        when(khatianService.verifyByOwnerName("Ramesh", "922855")).thenReturn(List.of(record));

        mockMvc.perform(post("/khatian_services/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request("owner", "Ramesh"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Records found"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].khatianNo").value("121"));
    }

    @Test
    void verifySearchesByKhatianNo() throws Exception {
        when(khatianService.verifyByKhatianNo("121", "922855")).thenReturn(record());

        mockMvc.perform(post("/khatian_services/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request("khatian", "121"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Khatian found"))
                .andExpect(jsonPath("$.data.khatianNo").value("121"));
    }

    @Test
    void verifySearchesByPlotNo() throws Exception {
        when(khatianService.verifyByPlotNo("45", "922855")).thenReturn(record());

        mockMvc.perform(post("/khatian_services/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request("plot", "45"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Plot found"))
                .andExpect(jsonPath("$.data.plotNo").value("45"));
    }

    @Test
    void verifyRejectsInvalidRequest() throws Exception {
        mockMvc.perform(post("/khatian_services/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void verifyReturnsNotFoundWhenNoRecordExists() throws Exception {
        when(khatianService.verifyByKhatianNo("999", "922855"))
                .thenThrow(new KhatianNotFoundException("Record not found"));

        mockMvc.perform(post("/khatian_services/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request("khatian", "999"))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Record not found"));
    }

    private KhatianResponseDTO record() {
        return KhatianResponseDTO.builder()
                .khatianNo("121")
                .ownerName("Ramesh Das")
                .plotNo("45")
                .villageCode("922855")
                .build();
    }

    private VerifyPayload request(String searchKey, String searchValue) {
        return new VerifyPayload(searchKey, searchValue, "922855");
    }

    private record VerifyPayload(String search_key, String search_value, String lgd_village_code) {
    }
}
