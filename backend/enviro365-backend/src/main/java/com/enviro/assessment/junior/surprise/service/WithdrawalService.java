package com.enviro.assessment.junior.surprise.service;

import com.enviro.assessment.junior.surprise.dto.WithdrawalRequest;
import com.enviro.assessment.junior.surprise.dto.WithdrawalResponse;
import com.enviro.assessment.junior.surprise.entity.Investor;
import com.enviro.assessment.junior.surprise.entity.Product;
import com.enviro.assessment.junior.surprise.entity.WithdrawalNotice;
import com.enviro.assessment.junior.surprise.exception.InvestorNotFoundException;
import com.enviro.assessment.junior.surprise.exception.ResourceNotFoundException;
import com.enviro.assessment.junior.surprise.exception.WithdrawalException;
import com.enviro.assessment.junior.surprise.mapper.EntityMapper;
import com.enviro.assessment.junior.surprise.repository.InvestorRepository;
import com.enviro.assessment.junior.surprise.repository.ProductRepository;
import com.enviro.assessment.junior.surprise.repository.WithdrawalNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WithdrawalService {

    private final InvestorRepository investorRepository;
    private final ProductRepository productRepository;
    private final WithdrawalNoticeRepository withdrawalNoticeRepository;

    public WithdrawalResponse createWithdrawalNotice(WithdrawalRequest request) {
        Investor investor = investorRepository.findById(request.investorId())
                .orElseThrow(() ->
                        new InvestorNotFoundException("Investor not found with ID: " + request.investorId())
                );

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with ID: " + request.productId())
                );

        validateProductBelongsToInvestor(product, investor);
        validateWithdrawalRules(investor, product, request.withdrawalAmount());

        WithdrawalNotice notice = WithdrawalNotice.builder()
                .investor(investor)
                .product(product)
                .withdrawalAmount(request.withdrawalAmount())
                .withdrawalDate(LocalDateTime.now())
                .status("APPROVED")
                .message("Withdrawal notice created successfully")
                .build();

        WithdrawalNotice savedNotice = withdrawalNoticeRepository.save(notice);

        return EntityMapper.toWithdrawalResponse(savedNotice);
    }

    public List<WithdrawalResponse> getAllWithdrawalNotices() {
        return withdrawalNoticeRepository.findAll()
                .stream()
                .map(EntityMapper::toWithdrawalResponse)
                .toList();
    }

    private void validateProductBelongsToInvestor(Product product, Investor investor) {
        if (!product.getInvestor().getId().equals(investor.getId())) {
            throw new WithdrawalException("Selected product does not belong to the investor");
        }
    }

    private void validateWithdrawalRules(Investor investor, Product product, BigDecimal withdrawalAmount) {
        if ("RETIREMENT".equalsIgnoreCase(product.getProductType()) && investor.getAge() <= 65) {
            throw new WithdrawalException("Retirement withdrawals are only allowed if investor age is greater than 65");
        }

        if (withdrawalAmount.compareTo(product.getCurrentBalance()) > 0) {
            throw new WithdrawalException("Withdrawal amount must not exceed available balance");
        }

        BigDecimal ninetyPercentOfBalance = product.getCurrentBalance()
                .multiply(BigDecimal.valueOf(0.90));

        if (withdrawalAmount.compareTo(ninetyPercentOfBalance) > 0) {
            throw new WithdrawalException("Withdrawal amount must not exceed 90% of current balance");
        }
    }

    public String exportWithdrawalsToCsv() {

        StringBuilder csv = new StringBuilder();

        csv.append("Id,Investor,Product,Amount,Date,Status\n");

        withdrawalNoticeRepository.findAll().forEach(withdrawal -> {

            csv.append(withdrawal.getId()).append(",");
            csv.append(withdrawal.getInvestor().getFullName()).append(",");
            csv.append(withdrawal.getProduct().getProductName()).append(",");
            csv.append(withdrawal.getWithdrawalAmount()).append(",");
            csv.append(withdrawal.getWithdrawalDate()).append(",");
            csv.append(withdrawal.getStatus()).append("\n");

        });

        return csv.toString();
    }
}