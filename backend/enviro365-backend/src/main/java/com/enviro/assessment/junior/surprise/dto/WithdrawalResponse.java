package com.enviro.assessment.junior.surprise.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WithdrawalResponse(
        Long id,
        String investorName,
        String productName,
        BigDecimal withdrawalAmount,
        LocalDateTime withdrawalDate,
        String status,
        String message
) {
}