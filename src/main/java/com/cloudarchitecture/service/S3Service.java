package com.cloudarchitecture.service;

import com.cloudarchitecture.common.ApiResponse;
import com.cloudarchitecture.dto.response.CloudFrontUrlResponse;
import com.cloudarchitecture.dto.response.FileUploadResponse;
import com.cloudarchitecture.entity.Member;
import com.cloudarchitecture.exception.FileUploadException;
import com.cloudarchitecture.exception.MemberNotFoundException;
import com.cloudarchitecture.exception.CloudFrontUrlGenerationException;
import com.cloudarchitecture.repository.MemberRepository;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final MemberRepository memberRepository;
    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.cloud.aws.cloudfront.domain}")
    private String cloudFrontDomain;

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

    public ApiResponse<CloudFrontUrlResponse> getcloudFrontUrl(Long memberId) {
        try {
            Member member = memberRepository.findById(memberId).orElseThrow(
                    () -> new MemberNotFoundException("해당 멤버가 없습니다.")
            );
            String key = member.getProfileImageUrl();
            String cloudFrontUrl = cloudFrontDomain + "/" + key;

            return ApiResponse.success(new CloudFrontUrlResponse(cloudFrontUrl));
        } catch (Exception e) {
            throw new CloudFrontUrlGenerationException("CloudFrontURL 생성에 실패했습니다");
        }
    }
}
