package com.example.paymentgateway.strategy;

import com.example.paymentgateway.dto.PaymentRequest;
import com.example.paymentgateway.dto.PaymentResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class WalletPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentResponse.PaymentResponseBuilder process(PaymentRequest request) {
        if (request.getPaymentDetails() == null || 
            !StringUtils.hasText(request.getPaymentDetails().getWalletProvider()) ||
            !StringUtils.hasText(request.getPaymentDetails().getMobileNumber())) {
            throw new IllegalArgumentException("Wallet provider and mobile number are required for Wallet payments");
        }
        
        return PaymentResponse.builder()
                .status("Success")
                .payment_type("WALLET")
                .bankcode(request.getPaymentDetails().getWalletProvider().toUpperCase());
    }
}
