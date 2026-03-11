package com.cloudarchitecture.exception;

import org.springframework.http.HttpStatus;

public class PresignedUrlGenerationException extends ServiceException {
    public PresignedUrlGenerationException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
