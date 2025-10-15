package com.NBWallet.layers.api.DTO.transactionDTO;

import com.NBWallet.layers.api.DTO.accountDTO.AccountInfoDto;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionItemDto {
    private Long id;
    private String number;
    private AccountInfoDto sourceAccount;
    private AccountInfoDto destinationAccount;
    private double fee;
    private double amount;
    private String currency;
    private String status;
    private LocalDateTime created;
    private LocalDateTime lastModified;
}