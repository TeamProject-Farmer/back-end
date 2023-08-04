package com.farmer.backend.domain.memberscoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberCouponRepository extends JpaRepository<MembersCoupon, Long> {

    Long countByMemberId(Long memberId);

    Optional<MembersCoupon> findById(Long membersCouponId);
}
