package com.example.loanpayback.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class LoanRate {

    @GeneratedValue
    @Id
    int id;

    String loanType;
    Double interestRate;
    Integer version;

    public LoanRate(final String loanType, final Double interestRate, final Integer version) {
        this.loanType = loanType;
        this.interestRate = interestRate;
        this.version = version;
    }
}
