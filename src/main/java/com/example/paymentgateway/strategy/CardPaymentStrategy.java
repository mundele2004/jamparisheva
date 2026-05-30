package com.example.paymentgateway.strategy;

import com.example.paymentgateway.dto.PaymentRequest;
import com.example.paymentgateway.dto.PaymentResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CardPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentResponse.PaymentResponseBuilder process(PaymentRequest request) {
        if (request.getPaymentDetails() == null || 
            !StringUtils.hasText(request.getPaymentDetails().getCardNumber()) ||
            !StringUtils.hasText(request.getPaymentDetails().getCvv())) {
            throw new IllegalArgumentException("Card number and CVV are required for Card payments");
        }
        
        // Mocking a successful card verification
        return PaymentResponse.builder()
                .status("Success")
                .payment_type("CARD")
                .bankcode("CARD000123");
    }
}
