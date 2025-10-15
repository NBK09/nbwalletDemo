package com.NBWallet.layers.api.DTO.accountDTO;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountTransactionLimitDto {
    private boolean isPermanently;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Double amount;
}