package com.farmer.backend.repository.admin.member;

import com.farmer.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {

}
