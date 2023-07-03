package com.example.loanpayback.constants;

import static com.example.loanpayback.constants.Limits.*;

public class Constants {

    private Constants() {}

    public static final String AMOUNT_MIN_MSG = "Amount must be at least " + AMOUNT_MIN;
    public static final String AMOUNT_MAX_MSG = "Amount cannot exceed " + AMOUNT_MAX;

    public static final String PAYBACK_TIME_YEARS_MIN_MSG = "Payback time in years must be at least " + PAYBACK_TIME_YEARS_MIN;
    public static final String PAYBACK_TIME_YEARS_MAX_MSG = "Payback time in years cannot exceed " + PAYBACK_TIME_YEARS_MAX;
}
