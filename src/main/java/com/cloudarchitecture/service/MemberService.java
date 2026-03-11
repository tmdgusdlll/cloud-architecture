package com.cloudarchitecture.service;

import com.cloudarchitecture.dto.request.MemberRequest;
import com.cloudarchitecture.dto.response.MemberResponse;
import com.cloudarchitecture.entity.Member;
import com.cloudarchitecture.exception.MemberNotFoundException;
import com.cloudarchitecture.repository.MemberRepository;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse save(MemberRequest request) {
        Member member = new Member(
                request.getName(),
                request.getAge(),
                request.getMbti()
        );
        Member savedMember = memberRepository.save(member);
        return new MemberResponse(savedMember);

    }

    public MemberResponse getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException("해당 멤버가 없습니다.")
        );
        return new MemberResponse(member);
    }
}
