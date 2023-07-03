package com.example.loanpayback.request;

import com.example.loanpayback.domain.LoanType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.Objects;

import static com.example.loanpayback.constants.Constants.AMOUNT_MIN_MSG;
import static com.example.loanpayback.constants.Constants.AMOUNT_MAX_MSG;
import static com.example.loanpayback.constants.Constants.PAYBACK_TIME_YEARS_MIN_MSG;
import static com.example.loanpayback.constants.Constants.PAYBACK_TIME_YEARS_MAX_MSG;
import static com.example.loanpayback.constants.Limits.AMOUNT_MIN;
import static com.example.loanpayback.constants.Limits.AMOUNT_MAX;
import static com.example.loanpayback.constants.Limits.PAYBACK_TIME_YEARS_MIN;
import static com.example.loanpayback.constants.Limits.PAYBACK_TIME_YEARS_MAX;
import static com.fasterxml.jackson.annotation.JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;

public class LoanPaybackRequest {

    @Min(value = AMOUNT_MIN, message = AMOUNT_MIN_MSG)
    @Max(value = AMOUNT_MAX, message = AMOUNT_MAX_MSG)
    private BigDecimal amount;

    @Min(value = PAYBACK_TIME_YEARS_MIN, message = PAYBACK_TIME_YEARS_MIN_MSG)
    @Max(value = PAYBACK_TIME_YEARS_MAX, message = PAYBACK_TIME_YEARS_MAX_MSG)
    private int paybackTimeYears;

    @JsonFormat(with = ACCEPT_CASE_INSENSITIVE_PROPERTIES)
    private LoanType loanType = LoanType.MORTGAGE;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getPaybackTimeYears() {
        return paybackTimeYears;
    }

    public void setPaybackTimeYears(int paybackTimeYears) {
        this.paybackTimeYears = paybackTimeYears;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanPaybackRequest that = (LoanPaybackRequest) o;
        return paybackTimeYears == that.paybackTimeYears && Objects.equals(amount, that.amount) && loanType == that.loanType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, paybackTimeYears, loanType);
    }
}
