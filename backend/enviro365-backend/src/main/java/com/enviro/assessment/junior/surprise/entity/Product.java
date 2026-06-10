package com.enviro.assessment.junior.surprise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private String productType;

    private BigDecimal currentBalance;

    @ManyToOne
    @JoinColumn(name = "investor_id")
    private Investor investor;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<WithdrawalNotice> withdrawalNotices = new ArrayList<>();
}