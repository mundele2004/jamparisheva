package com.example.paymentgateway.khatian.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KhatianResponseDTO {
    private String khatianNo;
    private String ownerName;
    private String plotNo;
    private String villageCode;
}
