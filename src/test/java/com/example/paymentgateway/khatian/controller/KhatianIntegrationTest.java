package com.example.paymentgateway.khatian.controller;

import com.example.paymentgateway.khatian.entity.KhatianMaster;
import com.example.paymentgateway.khatian.repository.KhatianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class KhatianIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KhatianRepository khatianRepository;

    @BeforeEach
    void setUp() {
        khatianRepository.deleteAll();
        khatianRepository.save(KhatianMaster.builder()
                .khatianNo("121")
                .plotNo("45")
                .ownerName("Ramesh Das")
                .lgdVillageCode("922855")
                .build());
    }

    @Test
    void verifyEndpointWorksWithDatabaseBackedOwnerSearch() throws Exception {
        mockMvc.perform(post("/khatian_services/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "search_key": "owner",
                                  "search_value": "ramesh",
                                  "lgd_village_code": "922855"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Records found"))
                .andExpect(jsonPath("$.data[0].khatianNo").value("121"))
                .andExpect(jsonPath("$.data[0].ownerName").value("Ramesh Das"))
                .andExpect(jsonPath("$.data[0].plotNo").value("45"))
                .andExpect(jsonPath("$.data[0].villageCode").value("922855"));
    }
}
