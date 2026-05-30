package com.example.paymentgateway.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String applicationNumber;
    
    private String amount;
    
    private String grn;
    
    private String cin;
    
    private String status;
    
    private String paymentMethod;
    
    private LocalDateTime timestamp;
}
