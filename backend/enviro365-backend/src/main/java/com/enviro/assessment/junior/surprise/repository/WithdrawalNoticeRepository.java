package com.enviro.assessment.junior.surprise.repository;

import com.enviro.assessment.junior.surprise.entity.WithdrawalNotice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawalNoticeRepository extends JpaRepository<WithdrawalNotice, Long> {

    List<WithdrawalNotice> findByInvestorId(Long investorId);
}