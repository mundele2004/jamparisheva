package com.example.paymentgateway.strategy;

import com.example.paymentgateway.dto.PaymentRequest;
import com.example.paymentgateway.dto.PaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class QrPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentResponse.PaymentResponseBuilder process(PaymentRequest request) {
        // Generate a mock UPI QR intent string
        String qrString = String.format("upi://pay?pa=merchant@okbank&pn=MockMerchant&am=%s&cu=INR&tn=Payment_for_%s", 
                request.getAmount(), request.getApplicationNumber());
                
        return PaymentResponse.builder()
                .status("Pending")
                .payment_type("QR")
                .bankcode("QRSYSTEM")
                .qr_string(qrString);
    }
}
