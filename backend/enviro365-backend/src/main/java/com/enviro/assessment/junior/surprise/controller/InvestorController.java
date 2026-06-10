package com.enviro.assessment.junior.surprise.controller;

import com.enviro.assessment.junior.surprise.dto.ErrorResponse;
import com.enviro.assessment.junior.surprise.dto.InvestorResponse;
import com.enviro.assessment.junior.surprise.service.InvestorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investors")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InvestorController {

    private final InvestorService investorService;

    @Operation(
            summary = "Get investor portfolio",
            description = "Returns investor details and all products belonging to the investor."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Investor portfolio returned successfully"),
            @ApiResponse(responseCode = "404", description = "Investor not found")
    })
    @GetMapping("/{investorId}/portfolio")
    public ResponseEntity<InvestorResponse> getInvestorPortfolio(@PathVariable Long investorId) {
        return ResponseEntity.ok(investorService.getInvestorPortfolio(investorId));
    }
}