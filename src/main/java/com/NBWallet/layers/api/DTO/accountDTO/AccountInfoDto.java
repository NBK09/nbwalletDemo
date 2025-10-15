package com.NBWallet.layers.api.DTO.accountDTO;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountInfoDto {
    private String number;
    private UserInfoDto user;
}