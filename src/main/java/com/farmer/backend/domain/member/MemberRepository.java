package com.farmer.backend.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {

    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByEmailAuth(String emailAuth);

    Optional<Member> findByRefreshToken(String refreshToken);

    Optional<Member> findBySocialId( String socialId);

}
