package com.enviro.assessment.junior.surprise.mapper;

import com.enviro.assessment.junior.surprise.dto.InvestorResponse;
import com.enviro.assessment.junior.surprise.dto.ProductResponse;
import com.enviro.assessment.junior.surprise.dto.WithdrawalResponse;
import com.enviro.assessment.junior.surprise.entity.Investor;
import com.enviro.assessment.junior.surprise.entity.Product;
import com.enviro.assessment.junior.surprise.entity.WithdrawalNotice;

import java.util.List;

public class EntityMapper {

    public static InvestorResponse toInvestorResponse(Investor investor) {
        List<ProductResponse> products = investor.getProducts()
                .stream()
                .map(EntityMapper::toProductResponse)
                .toList();

        return new InvestorResponse(
                investor.getId(),
                investor.getFullName(),
                investor.getAge(),
                investor.getEmail(),
                products
        );
    }

    public static ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getProductType(),
                product.getCurrentBalance()
        );
    }

    public static WithdrawalResponse toWithdrawalResponse(WithdrawalNotice notice) {
        return new WithdrawalResponse(
                notice.getId(),
                notice.getInvestor().getFullName(),
                notice.getProduct().getProductName(),
                notice.getWithdrawalAmount(),
                notice.getWithdrawalDate(),
                notice.getStatus(),
                notice.getMessage()
        );
    }
}