package com.cloudarchitecture.service;

import com.cloudarchitecture.common.ApiResponse;
import com.cloudarchitecture.dto.response.FileUploadResponse;
import com.cloudarchitecture.dto.response.PresignedUrlResponse;
import com.cloudarchitecture.entity.Member;
import com.cloudarchitecture.exception.FileUploadException;
import com.cloudarchitecture.exception.MemberNotFoundException;
import com.cloudarchitecture.exception.PresignedUrlGenerationException;
import com.cloudarchitecture.repository.MemberRepository;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final MemberRepository memberRepository;
    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public ApiResponse<FileUploadResponse> uploadProfileImage(Long memberId, MultipartFile file) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException("해당 멤버가 없습니다.")
        );
        try {
            String key = "uploads/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            s3Template.upload(bucket, key, file.getInputStream());

            member.updateProfileImageUrl(key);
            memberRepository.save(member);

            return ApiResponse.success(new FileUploadResponse(key));
        } catch (IOException e) {
            throw new FileUploadException("파일 업로드를 실패했습니다.");
        }
    }

    public ApiResponse<PresignedUrlResponse> getPresignedUrl(Long memberId) {
        try {
            Member member = memberRepository.findById(memberId).orElseThrow(
                    () -> new MemberNotFoundException("해당 멤버가 없습니다.")
            );
            String key = member.getProfileImageUrl();

            URL presignedUrl = s3Template.createSignedGetURL(bucket, key, Duration.ofDays(7));
            return ApiResponse.success(new PresignedUrlResponse(presignedUrl.toString()));
        } catch (Exception e) {
            throw new PresignedUrlGenerationException("Presigned URL 생성에 실패했습니다");
        }
    }
}
