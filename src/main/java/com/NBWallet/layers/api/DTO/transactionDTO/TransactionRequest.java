package com.NBWallet.layers.api.DTO.transactionDTO;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRequest {
    private double amount;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private String notes;
}