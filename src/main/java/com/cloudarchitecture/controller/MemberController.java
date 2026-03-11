package com.cloudarchitecture.controller;

import com.cloudarchitecture.common.ApiResponse;
import com.cloudarchitecture.dto.request.MemberRequest;
import com.cloudarchitecture.dto.response.FileUploadResponse;
import com.cloudarchitecture.dto.response.MemberResponse;
import com.cloudarchitecture.dto.response.PresignedUrlResponse;
import com.cloudarchitecture.service.MemberService;
import com.cloudarchitecture.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<MemberResponse> save(
            @RequestBody MemberRequest request
    ) {
        log.info("[API - LOG] POST /api/members - name: {}", request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.save(request));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> get(
            @PathVariable Long memberId
    ) {
        log.info("[API - LOG] GET /api/members/{}", memberId);
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember(memberId));
    }

    @PostMapping("/{memberId}/profile-image")
    public ResponseEntity<ApiResponse<FileUploadResponse>> save(
            @PathVariable Long memberId,
            @RequestParam("file") MultipartFile file
    ) {
        log.info("[API - LOG] POST /api/members/{}/profile-image", memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(s3Service.uploadProfileImage(memberId, file));
    }

    @GetMapping("/{memberId}/profile-image")
    public ResponseEntity<ApiResponse<PresignedUrlResponse>> getPresignedUrl(
            @PathVariable Long memberId
    ) {
        log.info("[API - LOG] GET /api/members/{}/profile-image", memberId);
        return ResponseEntity.status(HttpStatus.OK).body(s3Service.getPresignedUrl(memberId));
    }
}
