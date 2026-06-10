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

        Investor surprise = Investor.builder()
                .fullName("Surprise Ramatlhare")
                .age(67)
                .email("surprise.ramatlhare@email.com")
                .build();

        Product surpriseRetirement = Product.builder()
                .productName("Retirement Annuity")
                .productType("RETIREMENT")
                .currentBalance(new BigDecimal("500000.00"))
                .investor(surprise)
                .build();

        Product surpriseSavings = Product.builder()
                .productName("Tax Free Savings")
                .productType("SAVINGS")
                .currentBalance(new BigDecimal("120000.00"))
                .investor(surprise)
                .build();

        surprise.setProducts(List.of(surpriseRetirement, surpriseSavings));

        Investor kabelo = Investor.builder()
                .fullName("Kabelo Madisha")
                .age(69)
                .email("kabelo.madisha@email.com")
                .build();

        Product kabeloRetirement = Product.builder()
                .productName("Preservation Fund")
                .productType("RETIREMENT")
                .currentBalance(new BigDecimal("750000.00"))
                .investor(kabelo)
                .build();

        Product kabeloInvestment = Product.builder()
                .productName("Balanced Investment Portfolio")
                .productType("INVESTMENT")
                .currentBalance(new BigDecimal("250000.00"))
                .investor(kabelo)
                .build();

        kabelo.setProducts(List.of(kabeloRetirement, kabeloInvestment));

        Investor thato = Investor.builder()
                .fullName("Thato Singo")
                .age(45)
                .email("thato.singo@email.com")
                .build();

        Product thatoRetirement = Product.builder()
                .productName("Retirement Fund")
                .productType("RETIREMENT")
                .currentBalance(new BigDecimal("300000.00"))
                .investor(thato)
                .build();

        Product thatoSavings = Product.builder()
                .productName("Unit Trust")
                .productType("INVESTMENT")
                .currentBalance(new BigDecimal("90000.00"))
                .investor(thato)
                .build();

        thato.setProducts(List.of(thatoRetirement, thatoSavings));

        investorRepository.saveAll(List.of(surprise, kabelo, thato));
    }
}