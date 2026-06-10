package com.enviro.assessment.junior.surprise.controller;

import com.enviro.assessment.junior.surprise.dto.InvestorResponse;
import com.enviro.assessment.junior.surprise.service.InvestorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investors")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InvestorController {

    private final InvestorService investorService;

    @GetMapping("/{investorId}/portfolio")
    public ResponseEntity<InvestorResponse> getInvestorPortfolio(@PathVariable Long investorId) {
        return ResponseEntity.ok(investorService.getInvestorPortfolio(investorId));
    }
}