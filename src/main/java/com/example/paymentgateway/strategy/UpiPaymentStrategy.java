package com.example.paymentgateway.strategy;

import com.example.paymentgateway.dto.PaymentRequest;
import com.example.paymentgateway.dto.PaymentResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.UUID;

@Component
public class UpiPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentResponse.PaymentResponseBuilder process(PaymentRequest request) {
        if (request.getPaymentDetails() == null || 
            !StringUtils.hasText(request.getPaymentDetails().getUpiId())) {
            throw new IllegalArgumentException("UPI ID is required for UPI payments");
        }
        
        if (!request.getPaymentDetails().getUpiId().contains("@")) {
            throw new IllegalArgumentException("Invalid UPI ID format");
        }

        // Mocking successful UPI push
        return PaymentResponse.builder()
                .status("Success")
                .payment_type("UPI")
                .bankcode("UPI000999")
                .upi_transaction_id("UPI" + UUID.randomUUID().toString().replace("-", "").substring(0, 10));
    }
}
