package com.enviro.assessment.junior.surprise.service;

import com.enviro.assessment.junior.surprise.dto.WithdrawalRequest;
import com.enviro.assessment.junior.surprise.dto.WithdrawalResponse;
import com.enviro.assessment.junior.surprise.entity.Investor;
import com.enviro.assessment.junior.surprise.entity.Product;
import com.enviro.assessment.junior.surprise.entity.WithdrawalNotice;
import com.enviro.assessment.junior.surprise.exception.InvestorNotFoundException;
import com.enviro.assessment.junior.surprise.exception.ResourceNotFoundException;
import com.enviro.assessment.junior.surprise.exception.WithdrawalException;
import com.enviro.assessment.junior.surprise.repository.InvestorRepository;
import com.enviro.assessment.junior.surprise.repository.ProductRepository;
import com.enviro.assessment.junior.surprise.repository.WithdrawalNoticeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WithdrawalServiceTest {

    @Mock
    private InvestorRepository investorRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WithdrawalNoticeRepository withdrawalNoticeRepository;

    @InjectMocks
    private WithdrawalService withdrawalService;

    @Test
    void shouldCreateValidWithdrawal() {

        Investor investor = Investor.builder()
                .id(1L)
                .fullName("John Smith")
                .age(70)
                .build();

        Product product = Product.builder()
                .id(1L)
                .productName("Retirement Fund")
                .productType("RETIREMENT")
                .currentBalance(new BigDecimal("100000"))
                .investor(investor)
                .build();

        WithdrawalRequest request =
                new WithdrawalRequest(
                        1L,
                        1L,
                        new BigDecimal("50000")
                );

        when(investorRepository.findById(1L))
                .thenReturn(Optional.of(investor));

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(withdrawalNoticeRepository.save(any(WithdrawalNotice.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        WithdrawalResponse response =
                withdrawalService.createWithdrawalNotice(request);

        assertNotNull(response);

        verify(withdrawalNoticeRepository, times(1))
                .save(any(WithdrawalNotice.class));
    }

    @Test
    void shouldThrowInvestorNotFoundException() {

        WithdrawalRequest request =
                new WithdrawalRequest(
                        99L,
                        1L,
                        new BigDecimal("1000")
                );

        when(investorRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                InvestorNotFoundException.class,
                () -> withdrawalService.createWithdrawalNotice(request)
        );
    }

    @Test
    void shouldThrowProductNotFoundException() {

        Investor investor = Investor.builder()
                .id(1L)
                .age(70)
                .build();

        WithdrawalRequest request =
                new WithdrawalRequest(
                        1L,
                        99L,
                        new BigDecimal("1000")
                );

        when(investorRepository.findById(1L))
                .thenReturn(Optional.of(investor));

        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> withdrawalService.createWithdrawalNotice(request)
        );
    }

    @Test
    void shouldThrowWhenProductDoesNotBelongToInvestor() {

        Investor investor1 = Investor.builder()
                .id(1L)
                .age(70)
                .build();

        Investor investor2 = Investor.builder()
                .id(2L)
                .age(70)
                .build();

        Product product = Product.builder()
                .id(1L)
                .productType("RETIREMENT")
                .currentBalance(new BigDecimal("100000"))
                .investor(investor2)
                .build();

        WithdrawalRequest request =
                new WithdrawalRequest(
                        1L,
                        1L,
                        new BigDecimal("1000")
                );

        when(investorRepository.findById(1L))
                .thenReturn(Optional.of(investor1));

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                WithdrawalException.class,
                () -> withdrawalService.createWithdrawalNotice(request)
        );
    }

    @Test
    void shouldThrowWhenWithdrawalExceedsBalance() {

        Investor investor = Investor.builder()
                .id(1L)
                .age(70)
                .build();

        Product product = Product.builder()
                .id(1L)
                .productType("RETIREMENT")
                .currentBalance(new BigDecimal("10000"))
                .investor(investor)
                .build();

        WithdrawalRequest request =
                new WithdrawalRequest(
                        1L,
                        1L,
                        new BigDecimal("20000")
                );

        when(investorRepository.findById(1L))
                .thenReturn(Optional.of(investor));

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                WithdrawalException.class,
                () -> withdrawalService.createWithdrawalNotice(request)
        );
    }

    @Test
    void shouldThrowWhenWithdrawalExceedsNinetyPercent() {

        Investor investor = Investor.builder()
                .id(1L)
                .age(70)
                .build();

        Product product = Product.builder()
                .id(1L)
                .productType("RETIREMENT")
                .currentBalance(new BigDecimal("100000"))
                .investor(investor)
                .build();

        WithdrawalRequest request =
                new WithdrawalRequest(
                        1L,
                        1L,
                        new BigDecimal("95000")
                );

        when(investorRepository.findById(1L))
                .thenReturn(Optional.of(investor));

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                WithdrawalException.class,
                () -> withdrawalService.createWithdrawalNotice(request)
        );
    }

    @Test
    void shouldThrowWhenRetirementWithdrawalUnderAge65() {

        Investor investor = Investor.builder()
                .id(1L)
                .age(60)
                .build();

        Product product = Product.builder()
                .id(1L)
                .productType("RETIREMENT")
                .currentBalance(new BigDecimal("100000"))
                .investor(investor)
                .build();

        WithdrawalRequest request =
                new WithdrawalRequest(
                        1L,
                        1L,
                        new BigDecimal("1000")
                );

        when(investorRepository.findById(1L))
                .thenReturn(Optional.of(investor));

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                WithdrawalException.class,
                () -> withdrawalService.createWithdrawalNotice(request)
        );
    }
}