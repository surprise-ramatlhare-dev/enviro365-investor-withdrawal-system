package com.enviro.assessment.junior.surprise.controller;

import com.enviro.assessment.junior.surprise.dto.WithdrawalRequest;
import com.enviro.assessment.junior.surprise.dto.WithdrawalResponse;
import com.enviro.assessment.junior.surprise.service.WithdrawalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/withdrawals")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    @Operation(
            summary = "Create withdrawal notice",
            description = "Creates a withdrawal notice after validating investor, product ownership, balance, 90% limit, and retirement age rule."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Withdrawal notice created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or business rule violation"),
            @ApiResponse(responseCode = "404", description = "Investor or product not found")
    })
    @PostMapping
    public ResponseEntity<WithdrawalResponse> createWithdrawalNotice(
            @Valid @RequestBody WithdrawalRequest request
    ) {
        WithdrawalResponse response = withdrawalService.createWithdrawalNotice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get all withdrawal notices",
            description = "Returns the full withdrawal history for all investors."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Withdrawal history returned successfully")
    })
    @GetMapping
    public ResponseEntity<List<WithdrawalResponse>> getAllWithdrawalNotices() {
        return ResponseEntity.ok(withdrawalService.getAllWithdrawalNotices());
    }

    @Operation(
            summary = "Export withdrawals as CSV",
            description = "Exports withdrawal notices as a CSV statement. Can optionally be filtered by investor ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "CSV statement exported successfully"),
            @ApiResponse(responseCode = "404", description = "Investor not found")
    })
    @GetMapping("/export")
    public ResponseEntity<String> exportWithdrawalsToCsv(
            @RequestParam(required = false) Long investorId
    ) {
        String csvData = withdrawalService.exportWithdrawalsToCsv(investorId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=withdrawal-notices.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvData);
    }
}