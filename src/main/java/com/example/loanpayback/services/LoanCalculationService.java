package com.example.loanpayback.services;

import com.example.loanpayback.repositories.LoanRateRepository;
import com.example.loanpayback.domain.LoanType;
import com.example.loanpayback.domain.PaybackPeriod;
import com.example.loanpayback.exception.UnsupportedOperationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.example.loanpayback.domain.LoanType.MORTGAGE;

@Service
public class LoanCalculationService {

    private final LoanRateRepository repository;

    public LoanCalculationService(final LoanRateRepository repository) {
        this.repository = repository;
    }

    public List<PaybackPeriod> generatePaybackPlan(final BigDecimal amount, final int paybackTimeYears, final LoanType loanType) {
        return switch (loanType) {
            case MORTGAGE -> calculateMortgagePayback(amount, paybackTimeYears);
            case CAR, SPENDING ->
                    throw new UnsupportedOperationException(String.format("Only %s calculation currently supported.", MORTGAGE));
        };
    }

    private List<PaybackPeriod> calculateMortgagePayback(final BigDecimal amount, final int paybackTimeYears) {
        double interestRate = repository.findByLoanType(MORTGAGE.name());
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(interestRate / 100 / 12);
        BigDecimal monthlyPayment = calculateMonthlyPayment(amount, paybackTimeYears, monthlyInterestRate);

        List<PaybackPeriod> paybackPlan = new ArrayList<>();
        BigDecimal remainingAmount = amount;
        BigDecimal totalInterest = BigDecimal.ZERO;

        for (int i = 1; i <= paybackTimeYears * 12; i++) {
            BigDecimal interest = remainingAmount.multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal principal = monthlyPayment.subtract(interest).setScale(2, RoundingMode.HALF_UP);
            remainingAmount = remainingAmount.subtract(principal);
            totalInterest = totalInterest.add(interest);

            PaybackPeriod period = new PaybackPeriod(i, monthlyPayment, interest, principal, remainingAmount, totalInterest);
            paybackPlan.add(period);
        }

        return paybackPlan;
    }

    private BigDecimal calculateMonthlyPayment(final BigDecimal loanAmount, final int paybackTime, final BigDecimal monthlyInterestRate) {
        BigDecimal pow = BigDecimal.ONE.add(monthlyInterestRate).pow(paybackTime * 12);
        BigDecimal factor = monthlyInterestRate.multiply(pow);
        BigDecimal numerator = loanAmount.multiply(factor);
        BigDecimal denominator = pow.subtract(BigDecimal.ONE);
        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }
}
