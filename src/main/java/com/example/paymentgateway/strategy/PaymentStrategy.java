package com.example.paymentgateway.strategy;

import com.example.paymentgateway.dto.PaymentRequest;
import com.example.paymentgateway.dto.PaymentResponse;

public interface PaymentStrategy {
    PaymentResponse.PaymentResponseBuilder process(PaymentRequest request);
}
