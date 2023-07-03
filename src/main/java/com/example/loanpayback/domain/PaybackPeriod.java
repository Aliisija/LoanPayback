package com.example.loanpayback.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class PaybackPeriod {
    private int period;
    private BigDecimal monthlyPayment;
    private BigDecimal interest;

    private BigDecimal principal;
    private BigDecimal remainingAmount;
    private BigDecimal totalInterest;

    public PaybackPeriod(final int period, final BigDecimal monthlyPayment, final BigDecimal interest,
                         final BigDecimal principal, final BigDecimal remainingAmount, final BigDecimal totalInterest) {
        this.period = period;
        this.monthlyPayment = monthlyPayment;
        this.interest = interest;
        this.principal = principal;
        this.remainingAmount = remainingAmount;
        this.totalInterest = totalInterest;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public BigDecimal getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(BigDecimal totalInterest) {
        this.totalInterest = totalInterest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaybackPeriod that = (PaybackPeriod) o;
        return period == that.period && monthlyPayment.equals(that.monthlyPayment) && interest.equals(that.interest) && principal.equals(that.principal) && remainingAmount.equals(that.remainingAmount) && totalInterest.equals(that.totalInterest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(period, monthlyPayment, interest, principal, remainingAmount, totalInterest);
    }
}
