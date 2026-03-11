package com.cloudarchitecture.repository;

import com.cloudarchitecture.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
