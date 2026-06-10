package com.enviro.assessment.junior.surprise.service;

import com.enviro.assessment.junior.surprise.dto.InvestorResponse;
import com.enviro.assessment.junior.surprise.entity.Investor;
import com.enviro.assessment.junior.surprise.exception.InvestorNotFoundException;
import com.enviro.assessment.junior.surprise.repository.InvestorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvestorServiceTest {

    @Mock
    private InvestorRepository investorRepository;

    @InjectMocks
    private InvestorService investorService;

    @Test
    void getInvestorPortfolio_ShouldReturnInvestor_WhenInvestorExists() {
        Investor investor = Investor.builder()
                .id(1L)
                .fullName("John Smith")
                .age(70)
                .email("john.smith@email.com")
                .products(new ArrayList<>())
                .build();

        when(investorRepository.findById(1L)).thenReturn(Optional.of(investor));

        InvestorResponse response = investorService.getInvestorPortfolio(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("John Smith", response.fullName());
        assertEquals(70, response.age());
        assertEquals("john.smith@email.com", response.email());

        verify(investorRepository, times(1)).findById(1L);
    }

    @Test
    void getInvestorPortfolio_ShouldThrowException_WhenInvestorDoesNotExist() {
        when(investorRepository.findById(99L)).thenReturn(Optional.empty());

        InvestorNotFoundException exception = assertThrows(
                InvestorNotFoundException.class,
                () -> investorService.getInvestorPortfolio(99L)
        );

        assertEquals("Investor not found with ID: 99", exception.getMessage());

        verify(investorRepository, times(1)).findById(99L);
    }
}