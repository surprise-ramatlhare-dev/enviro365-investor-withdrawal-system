package com.enviro.assessment.junior.surprise.controller;

import com.enviro.assessment.junior.surprise.dto.WithdrawalRequest;
import com.enviro.assessment.junior.surprise.dto.WithdrawalResponse;
import com.enviro.assessment.junior.surprise.service.WithdrawalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/withdrawals")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    @PostMapping
    public ResponseEntity<WithdrawalResponse> createWithdrawalNotice(
            @Valid @RequestBody WithdrawalRequest request
    ) {
        WithdrawalResponse response = withdrawalService.createWithdrawalNotice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}