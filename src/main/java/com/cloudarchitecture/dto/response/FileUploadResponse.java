package com.cloudarchitecture.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class FileUploadResponse {

    private final String imageUrl; // S3에 저장된 이미지 URL

    public FileUploadResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
