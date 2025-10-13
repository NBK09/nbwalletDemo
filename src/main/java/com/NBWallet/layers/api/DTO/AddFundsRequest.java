package com.NBWallet.layers.api.DTO;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddFundsRequest {
    private String accountNumber;
    private double amount;
}