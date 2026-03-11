package com.cloudarchitecture.dto.response;

import lombok.Getter;

@Getter
public class PresignedUrlResponse {

    private final String presignedUrl;

    public PresignedUrlResponse(String presignedUrl) {
        this.presignedUrl = presignedUrl;
    }
}
