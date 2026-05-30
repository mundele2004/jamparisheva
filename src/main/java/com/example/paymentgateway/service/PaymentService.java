package com.example.paymentgateway.service;

import com.example.paymentgateway.dto.PaymentMethod;
import com.example.paymentgateway.dto.PaymentRequest;
import com.example.paymentgateway.dto.PaymentResponse;
import com.example.paymentgateway.entity.Transaction;
import com.example.paymentgateway.repository.TransactionRepository;
import com.example.paymentgateway.strategy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TransactionRepository transactionRepository;
    private final CardPaymentStrategy cardPaymentStrategy;
    private final UpiPaymentStrategy upiPaymentStrategy;
    private final WalletPaymentStrategy walletPaymentStrategy;
    private final QrPaymentStrategy qrPaymentStrategy;

    private final Random random = new Random();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PaymentResponse processPayment(PaymentRequest request) {
        // 1. Generate core transaction parameters
        String grn = String.format("GRN%09d", random.nextInt(1_000_000_000));
        String cin = String.format("CIN%09d", random.nextInt(1_000_000_000));
        String tdate = LocalDate.now().format(dateFormatter);

        // 2. Determine Strategy based on PaymentMethod
        PaymentStrategy strategy = getStrategy(request.getPaymentMethod());

        // 3. Process using the Strategy
        PaymentResponse.PaymentResponseBuilder responseBuilder = strategy.process(request);

        // 4. Populate common fields
        responseBuilder
                .applicationNumber(request.getApplicationNumber())
                .amount(request.getAmount())
                .grn(grn)
                .cin(cin)
                .tdate(tdate)
                .hash("c3f8e9b2a1d4e5f6") // Mock hash
                .add_param1("static")
                .add_param2("static")
                .add_param3("static");

        PaymentResponse finalResponse = responseBuilder.build();

        // 5. Save Transaction to DB
        Transaction transaction = Transaction.builder()
                .applicationNumber(request.getApplicationNumber())
                .amount(request.getAmount())
                .grn(grn)
                .cin(cin)
                .status(finalResponse.getStatus())
                .paymentMethod(request.getPaymentMethod().name())
                .timestamp(LocalDateTime.now())
                .build();
        
        transactionRepository.save(transaction);

        return finalResponse;
    }

    private PaymentStrategy getStrategy(PaymentMethod method) {
        switch (method) {
            case CREDIT_CARD:
            case DEBIT_CARD:
                return cardPaymentStrategy;
            case UPI:
                return upiPaymentStrategy;
            case WALLET:
                return walletPaymentStrategy;
            case QR:
                return qrPaymentStrategy;
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
    }
}
