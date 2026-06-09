package com.example.paymentgateway.khatian.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyRequestDTO {

    @NotBlank(message = "search_key is required")
    @JsonProperty("search_key")
    private String searchKey;

    @NotBlank(message = "search_value is required")
    @JsonProperty("search_value")
    private String searchValue;

    @NotBlank(message = "lgd_village_code is required")
    @JsonProperty("lgd_village_code")
    private String lgdVillageCode;
}
