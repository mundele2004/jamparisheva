package com.example.paymentgateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotBlank(message = "Applicationnumber is required")
    @Size(min = 13, max = 13, message = "Applicationnumber must be exactly 13 characters long")
    @JsonProperty("Applicationnumber")
    private String applicationNumber;

    @NotBlank(message = "amount is required")
    private String amount;

    @NotNull(message = "paymentMethod is required")
    private PaymentMethod paymentMethod;

    private PaymentDetails paymentDetails;
}
