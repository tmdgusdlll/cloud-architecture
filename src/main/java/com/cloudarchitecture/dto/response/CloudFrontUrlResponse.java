package com.cloudarchitecture.dto.response;

import lombok.Getter;

@Getter
public class CloudFrontUrlResponse {

    private final String cloudFrontUrl;

    public CloudFrontUrlResponse(String cloudFrontUrl) {
        this.cloudFrontUrl = cloudFrontUrl;
    }
}
