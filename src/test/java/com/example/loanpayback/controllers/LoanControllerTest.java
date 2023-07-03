package com.example.loanpayback.controllers;

import com.example.loanpayback.request.LoanPaybackRequest;
import com.example.loanpayback.services.LoanService;
import com.example.loanpayback.domain.LoanType;
import com.example.loanpayback.domain.PaybackPeriod;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static helpers.TestHelpers.createLoanPaybackRequest;
import static helpers.TestHelpers.createPaybackPeriod;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService service;

    @Test
    void calculateLoan_success() throws Exception {
        List<PaybackPeriod> calculation = new ArrayList<>();
        calculation.add(createPaybackPeriod());

        LoanPaybackRequest request = createLoanPaybackRequest();

        when(service.calculatePayback(request)).thenReturn(calculation);

        MvcResult result = mockMvc.perform(post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request))
        ).andExpect(status().isOk()).andReturn();

        assertEquals(new ObjectMapper().writeValueAsString(calculation), result.getResponse().getContentAsString());
    }

    @Test
    void calculateLoan_loanTypeProvided_success() throws Exception {
        List<PaybackPeriod> calculation = new ArrayList<>();
        calculation.add(createPaybackPeriod());
        String userRequest = """
                    {
                        "amount": 20000,
                        "paybackTimeYears": 5,
                        "loanType": "MORTGAGE"
                    }
                """;

        ArgumentCaptor<LoanPaybackRequest> requestCaptor = ArgumentCaptor.forClass(LoanPaybackRequest.class);

        when(service.calculatePayback(any())).thenReturn(calculation);

        MvcResult result = mockMvc.perform(post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRequest)
        ).andExpect(status().isOk()).andReturn();

        verify(service).calculatePayback(requestCaptor.capture());

        LoanPaybackRequest capturedRequest = requestCaptor.getValue();
        assertEquals(BigDecimal.valueOf(20000), capturedRequest.getAmount());
        assertEquals(5, capturedRequest.getPaybackTimeYears());
        assertEquals(LoanType.MORTGAGE, capturedRequest.getLoanType());

        assertEquals(new ObjectMapper().writeValueAsString(calculation), result.getResponse().getContentAsString());
    }

    @Test
    void calculateLoan_loanTypeProvided_lowercase_success() throws Exception {
        List<PaybackPeriod> calculation = new ArrayList<>();
        calculation.add(createPaybackPeriod());
        String request = """
                    {
                        "amount": 20000,
                        "paybackTimeYears": 5,
                        "loanType": "mortgage"
                    }
                """;

        ArgumentCaptor<LoanPaybackRequest> requestCaptor = ArgumentCaptor.forClass(LoanPaybackRequest.class);

        when(service.calculatePayback(any())).thenReturn(calculation);

        MvcResult result = mockMvc.perform(post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isOk()).andReturn();

        verify(service).calculatePayback(requestCaptor.capture());

        LoanPaybackRequest capturedRequest = requestCaptor.getValue();
        assertEquals(BigDecimal.valueOf(20000), capturedRequest.getAmount());
        assertEquals(5, capturedRequest.getPaybackTimeYears());
        assertEquals(LoanType.MORTGAGE, capturedRequest.getLoanType());

        assertEquals(new ObjectMapper().writeValueAsString(calculation), result.getResponse().getContentAsString());
    }

    @Test
    void calculateLoan_unknownPropProvided() throws Exception {
        String request = """
                    {
                        "amount": 20000,
                        "paybackTimeYears": 5,
                        "unknownProp": "noo"
                    }
                """;

        String message = "{\"status\":\"BAD_REQUEST\",\"errors\":[\"Field 'unknownProp' is not recognized\"]}";

        testExceptionCase(request, message);
    }

    @Test
    void calculateLoan_wrongAmountProvided() throws Exception {
        String request = """
                    {
                        "amount": 0,
                        "paybackTimeYears": 5
                    }
                """;

        String message = "{\"status\":\"BAD_REQUEST\",\"errors\":[\"Amount must be at least 1\"]}";

        testExceptionCase(request, message);
    }

    @Test
    void calculateLoan_wrongPaybackTimeYearsProvided() throws Exception {
        String request = """
                    {
                        "amount": 20000,
                        "paybackTimeYears": 0
                    }
                """;

        String message = "{\"status\":\"BAD_REQUEST\",\"errors\":[\"Payback time in years must be at least 1\"]}";

        testExceptionCase(request, message);
    }

//    @Test
//    void calculateLoan_wrongPropsProvided() throws Exception {
//        String request = """
//                    {
//                        "amount": 500001,
//                        "paybackTimeYears": 101
//                    }
//                """;
//
//        String message = "{\"status\":\"BAD_REQUEST\",\"errors\":[\"Payback time in years cannot exceed 100\",\"Amount cannot exceed 500000\"]}";
//
//        testExceptionCase(request, message);
//    }

    @Test
    void calculateLoan_wrongLoanTypeProvided() throws Exception {
        String request = """
                    {
                        "amount": 20000,
                        "paybackTimeYears": 5,
                        "loanType": "wrong"
                    }
                """;

        String message = "{\"status\":\"BAD_REQUEST\",\"errors\":[\"Field 'loanType' is not valid\"]}";

        testExceptionCase(request, message);
    }

    @Test
    void calculateLoan_wrongJson() throws Exception {
        String request = """
                    {
                        "amount": 20000,
                        "paybackTimeYears": /
                    }
                """;

        String message = "{\"status\":\"BAD_REQUEST\",\"errors\":[\"Failed to parse the JSON\"]}";

        testExceptionCase(request, message);
    }

    @Test
    void calculateLoan_wrongPropsProvided() throws Exception {
        String request = """
                    {
                        "amount": 500001,
                        "paybackTimeYears": 101
                    }
                """;

        MvcResult result = mockMvc.perform(post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Payback time in years cannot exceed 100"));
        assertTrue(result.getResponse().getContentAsString().contains("Amount cannot exceed 500000"));
    }

    private void testExceptionCase(String request, String errorMessage) throws Exception {
        MvcResult result = mockMvc.perform(post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isBadRequest()).andReturn();

        assertEquals(errorMessage, result.getResponse().getContentAsString());
    }

}