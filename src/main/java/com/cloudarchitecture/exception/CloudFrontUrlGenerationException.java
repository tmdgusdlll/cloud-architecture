package com.cloudarchitecture.exception;

import org.springframework.http.HttpStatus;

public class CloudFrontUrlGenerationException extends ServiceException {
    public CloudFrontUrlGenerationException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
