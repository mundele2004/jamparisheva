package com.example.paymentgateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse {
    @JsonProperty("Applicationnumber")
    private String applicationNumber;
    
    private String amount;
    
    @JsonProperty("GRN")
    private String grn;
    
    private String status;
    
    @JsonProperty("CIN")
    private String cin;
    
    private String tdate;
    private String payment_type;
    private String bankcode;
    private String hash;
    private String add_param1;
    private String add_param2;
    private String add_param3;

    // Additional fields for specific payment methods
    private String qr_string;
    private String upi_transaction_id;
}
