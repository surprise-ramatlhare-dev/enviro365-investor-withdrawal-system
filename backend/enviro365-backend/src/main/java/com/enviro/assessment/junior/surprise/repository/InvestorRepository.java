package com.enviro.assessment.junior.surprise.repository;

import com.enviro.assessment.junior.surprise.entity.Investor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestorRepository extends JpaRepository<Investor, Long> {
}