package com.enviro.assessment.junior.surprise.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record WithdrawalRequest(
        @NotNull(message = "Investor ID is required")
        Long investorId,

        @NotNull(message = "Product ID is required")
        Long productId,

        @NotNull(message = "Withdrawal amount is required")
        @Positive(message = "Withdrawal amount must be greater than zero")
        BigDecimal withdrawalAmount
) {
}