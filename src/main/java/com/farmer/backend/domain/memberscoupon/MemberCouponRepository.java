package com.farmer.backend.domain.memberscoupon;

import com.farmer.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MembersCoupon, Long> {

    Long countByMemberId(Long memberId);
}
