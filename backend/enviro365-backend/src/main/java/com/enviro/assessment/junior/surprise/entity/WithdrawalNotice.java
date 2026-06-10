package com.enviro.assessment.junior.surprise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawalNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal withdrawalAmount;

    private LocalDateTime withdrawalDate;

    private String status;

    private String message;

    @ManyToOne
    @JoinColumn(name = "investor_id")
    private Investor investor;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}