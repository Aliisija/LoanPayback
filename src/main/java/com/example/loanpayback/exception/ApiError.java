package com.example.loanpayback.exception;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

public class ApiError {

    private HttpStatus status;
    private List<String> errors;

    public ApiError(HttpStatus status, List<String> errors) {
        super();
        this.status = status;
        this.errors = errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiError apiError = (ApiError) o;
        return status == apiError.status && Objects.equals(errors, apiError.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, errors);
    }
}

