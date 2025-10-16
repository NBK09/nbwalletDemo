package com.NBWallet.layers.db.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "\"AccountPlan\"")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"Id\"")
    Integer id;
    @Column(name = "\"Name\"")
    String name;
    @Column(name = "\"AnnualServicePrice\"")
    Double annualServicePrice;
}
