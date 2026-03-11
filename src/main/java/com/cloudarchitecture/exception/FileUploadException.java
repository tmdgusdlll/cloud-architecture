package com.cloudarchitecture.exception;

import org.springframework.http.HttpStatus;

public class FileUploadException extends ServiceException {
    public FileUploadException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
