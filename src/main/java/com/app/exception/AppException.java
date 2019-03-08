package com.app.exception;

public class AppException extends RuntimeException {

    private String exceptionMessage;

    public AppException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
