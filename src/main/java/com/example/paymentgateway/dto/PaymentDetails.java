package com.example.paymentgateway.dto;

import lombok.Data;

@Data
public class PaymentDetails {
    // For cards
    private String cardNumber;
    private String cvv;
    private String expiry;
    
    // For UPI
    private String upiId;
    
    // For Wallets
    private String walletProvider;
    private String mobileNumber;
}
