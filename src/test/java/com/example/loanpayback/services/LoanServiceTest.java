package com.example.loanpayback.services;

import com.example.loanpayback.domain.LoanType;
import com.example.loanpayback.repositories.LoanRateRepository;
import com.example.loanpayback.request.LoanPaybackRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.example.loanpayback.domain.LoanType.MORTGAGE;
import static helpers.TestHelpers.createLoanPaybackRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class LoanServiceTest {

    @Autowired
    LoanService loanService;

    @MockBean
    LoanCalculationService calculationService;

    @MockBean
    LoanRateRepository repository;

    @Test
    void callService_success() {
        LoanPaybackRequest request = createLoanPaybackRequest();

        ArgumentCaptor<BigDecimal> amountCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        ArgumentCaptor<Integer> timeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<LoanType> loanTypeCaptor = ArgumentCaptor.forClass(LoanType.class);
        when(calculationService.generatePaybackPlan(amountCaptor.capture(), timeCaptor.capture(), loanTypeCaptor.capture())).thenReturn(new ArrayList<>());

        loanService.calculatePayback(request);

        assertEquals(BigDecimal.valueOf(20000), amountCaptor.getValue());
        assertEquals(5, timeCaptor.getValue());
        assertEquals(MORTGAGE, loanTypeCaptor.getValue());
    }
}