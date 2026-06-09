package com.example.paymentgateway.khatian.dto;

public class VerifyResponseDTO<T> extends ApiResponseDTO<T> {
    public VerifyResponseDTO(boolean success, String message, T data) {
        super(success, message, data);
    }
}
