package com.example.haccpbackend.handler;

import java.util.Map;
import java.util.Set;

public class ExceptionResponseBuilder {

    private Integer businessErrorCode;
    private String businessErrorDescription;
    private String error;
    private Set<String> validationErrors;
    private Map<String, String> errors;

    public ExceptionResponseBuilder() {
    }

    public ExceptionResponseBuilder businessErrorCode(Integer businessErrorCode) {
        this.businessErrorCode = businessErrorCode;
        return this;
    }

    public ExceptionResponseBuilder businessErrorDescription(String businessErrorDescription) {
        this.businessErrorDescription = businessErrorDescription;
        return this;
    }

    public ExceptionResponseBuilder error(String error) {
        this.error = error;
        return this;
    }

    public ExceptionResponseBuilder validationErrors(Set<String> validationErrors) {
        this.validationErrors = validationErrors;
        return this;
    }

    public ExceptionResponseBuilder errors(Map<String, String> errors) {
        this.errors = errors;
        return this;
    }

    public ExceptionResponse build() {
        return new ExceptionResponse(businessErrorCode, businessErrorDescription, error, validationErrors, errors);
    }
}

