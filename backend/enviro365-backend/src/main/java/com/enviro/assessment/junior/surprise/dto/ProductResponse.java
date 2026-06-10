package com.enviro.assessment.junior.surprise.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String productName,
        String productType,
        BigDecimal currentBalance
) {
}