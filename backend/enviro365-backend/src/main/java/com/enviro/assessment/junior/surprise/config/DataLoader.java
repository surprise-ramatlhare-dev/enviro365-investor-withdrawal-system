package com.enviro.assessment.junior.surprise.config;

import com.enviro.assessment.junior.surprise.entity.Investor;
import com.enviro.assessment.junior.surprise.entity.Product;
import com.enviro.assessment.junior.surprise.repository.InvestorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final InvestorRepository investorRepository;

    @Override
    public void run(String... args) {
        if (investorRepository.count() > 0) {
            return;
        }

        Investor john = Investor.builder()
                .fullName("John Smith")
                .age(70)
                .email("john.smith@email.com")
                .build();

        Product johnRetirement = Product.builder()
                .productName("Retirement Annuity")
                .productType("RETIREMENT")
                .currentBalance(new BigDecimal("500000.00"))
                .investor(john)
                .build();

        Product johnSavings = Product.builder()
                .productName("Tax Free Savings")
                .productType("SAVINGS")
                .currentBalance(new BigDecimal("120000.00"))
                .investor(john)
                .build();

        john.setProducts(List.of(johnRetirement, johnSavings));

        Investor mary = Investor.builder()
                .fullName("Mary Johnson")
                .age(60)
                .email("mary.johnson@email.com")
                .build();

        Product maryRetirement = Product.builder()
                .productName("Retirement Fund")
                .productType("RETIREMENT")
                .currentBalance(new BigDecimal("300000.00"))
                .investor(mary)
                .build();

        Product maryInvestment = Product.builder()
                .productName("Unit Trust")
                .productType("INVESTMENT")
                .currentBalance(new BigDecimal("90000.00"))
                .investor(mary)
                .build();

        mary.setProducts(List.of(maryRetirement, maryInvestment));

        investorRepository.saveAll(List.of(john, mary));
    }
}