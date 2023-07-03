package com.example.loanpayback.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, listOf("Failed to parse the JSON"));
        Throwable cause = ex.getCause();
        if (cause != null) {
            if (cause instanceof UnrecognizedPropertyException e) {
                String problemField = getProblemField((e).getPath());
                apiError = new ApiError(HttpStatus.BAD_REQUEST, listOf(String.format("Field '%s' is not recognized", problemField)));
            }

            if (cause instanceof InvalidFormatException e) {
                String problemField = getProblemField((e).getPath());
                apiError = new ApiError(HttpStatus.BAD_REQUEST, listOf(String.format("Field '%s' is not valid", problemField)));
            }
        }

        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleUnsupportedOperationException(UnsupportedOperationException ex) {
        return new ApiError(HttpStatus.BAD_REQUEST, listOf(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiError handleAllUncaughtException(Exception ex) {
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, listOf("Something went wrong"));
    }

    private String getProblemField(List<JsonMappingException.Reference> path) {
        return path.get(0).getFieldName();
    }
}
