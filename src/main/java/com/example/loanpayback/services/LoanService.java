package com.example.loanpayback.services;

import com.example.loanpayback.request.LoanPaybackRequest;
import com.example.loanpayback.domain.LoanRate;
import com.example.loanpayback.repositories.LoanRateRepository;
import com.example.loanpayback.domain.PaybackPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.loanpayback.domain.LoanType.CAR;
import static com.example.loanpayback.domain.LoanType.SPENDING;
import static com.example.loanpayback.domain.LoanType.MORTGAGE;

@Service
public class LoanService {

    private final LoanCalculationService calculationService;

    @Autowired
    public LoanService(final LoanRateRepository repo, final LoanCalculationService calculationService) {
        this.calculationService = calculationService;

        repo.save(new LoanRate(CAR.name(), 1.2, 1));
        repo.save(new LoanRate(SPENDING.name(), 2.1, 1));
        repo.save(new LoanRate(MORTGAGE.name(), 3.3, 1));
        repo.save(new LoanRate(MORTGAGE.name(), 3.4, 2));
        repo.save(new LoanRate(MORTGAGE.name(), 3.5, 3));
    }

    public List<PaybackPeriod> calculatePayback(LoanPaybackRequest request) {
        return calculationService.generatePaybackPlan(request.getAmount(), request.getPaybackTimeYears(), request.getLoanType());
    }
}
