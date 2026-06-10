package com.enviro.assessment.junior.surprise.service;

import com.enviro.assessment.junior.surprise.dto.InvestorResponse;
import com.enviro.assessment.junior.surprise.entity.Investor;
import com.enviro.assessment.junior.surprise.exception.InvestorNotFoundException;
import com.enviro.assessment.junior.surprise.mapper.EntityMapper;
import com.enviro.assessment.junior.surprise.repository.InvestorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvestorService {

    private final InvestorRepository investorRepository;

    public InvestorResponse getInvestorPortfolio(Long investorId) {
        Investor investor = investorRepository.findById(investorId)
                .orElseThrow(() ->
                        new InvestorNotFoundException("Investor not found with ID: " + investorId)
                );

        return EntityMapper.toInvestorResponse(investor);
    }
}