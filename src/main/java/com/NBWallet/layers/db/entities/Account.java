package com.NBWallet.layers.db.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "\"Account\"")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    Integer Id;
    @Column(name = "\"Number\"")
    String number;
    @Column(name = "\"Balance\"")
    Double balance;
    @Column(name = "\"Currency\"")
    Integer currency;
    @Column(name = "\"Status\"")
    Integer status;
    @Column(name = "\"UserId\"")
    Integer userId;
    @Column(name = "\"AccountPlanId\"")
    Integer accountPlanId;
    @Column(name = "\"CreatedDate\"")
    Date createdDate;
}
