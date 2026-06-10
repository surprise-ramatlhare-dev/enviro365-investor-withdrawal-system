package com.enviro.assessment.junior.surprise.dto;

import java.util.List;

public record InvestorResponse(
        Long id,
        String fullName,
        Integer age,
        String email,
        List<ProductResponse> products
) {
}