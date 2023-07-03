package com.example.loanpayback.services;

import com.example.loanpayback.domain.PaybackPeriod;
import com.example.loanpayback.repositories.LoanRateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;

import static com.example.loanpayback.domain.LoanType.CAR;
import static com.example.loanpayback.domain.LoanType.MORTGAGE;
import static helpers.TestHelpers.getCalculationResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class LoanCalculationServiceTest {

    @Autowired
    LoanCalculationService calculationService;

    @MockBean
    LoanRateRepository repository;

    @Test
    void calculateMortgage_success() {
        when(repository.findByLoanType(MORTGAGE.name())).thenReturn(3.5);

        List<PaybackPeriod> result = calculationService.generatePaybackPlan(BigDecimal.valueOf(20000), 1, MORTGAGE);

        assertEquals(12, result.size());
        assertEquals(getCalculationResult(), result);
    }

    @Test
    void calculateMortgage_unsupportedType_error() {
        Exception e = new Exception();
        try {
            calculationService.generatePaybackPlan(BigDecimal.valueOf(20000), 1, CAR);
        } catch (Exception ex) {
            e = ex;
        }

        assertEquals("Only MORTGAGE calculation currently supported.", e.getMessage());
    }

}