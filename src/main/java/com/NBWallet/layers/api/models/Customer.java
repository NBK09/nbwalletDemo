package com.NBWallet.layers.api.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseEntity {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private String profileImage;
    private String userType;
    private boolean deleted;

    // Пароль и файл — если они используются при создании/редактировании
    private String password;
    private String file;
}
