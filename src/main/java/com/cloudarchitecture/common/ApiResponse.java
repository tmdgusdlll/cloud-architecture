package com.cloudarchitecture.common;


public record ApiResponse<T>(
        boolean success,       // 성공 여부 (true/false)
        T data,               // 성공 시 실제 데이터 (제네릭)
        ErrorResponse error   // 실패 시 에러 정보
) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static ApiResponse<Void> fail(ErrorResponse errorResponse) {
        return new ApiResponse<>(false, null, errorResponse);
    }

    public static ApiResponse<Void> fail(String code, String message) {
        return new ApiResponse<>(false, null, ErrorResponse.builder()
                .code(code)
                .message(message)
                .build());
    }
}
