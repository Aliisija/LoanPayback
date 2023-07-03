package com.example.loanpayback.controllers;

import com.example.loanpayback.request.LoanPaybackRequest;
import com.example.loanpayback.services.LoanService;
import com.example.loanpayback.domain.PaybackPeriod;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@Validated
public class LoanController {

    private final LoanService service;

    public LoanController(final LoanService service) {
        this.service = service;
    }

    @ResponseStatus(OK)
    @PostMapping("/calculate")
    public List<PaybackPeriod> calculate(
        @RequestBody @Valid final LoanPaybackRequest request
    ) {
        return service.calculatePayback(request);
    }
}

