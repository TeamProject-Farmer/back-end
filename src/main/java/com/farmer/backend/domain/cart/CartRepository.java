package com.farmer.backend.domain.cart;

import com.farmer.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long>, CartQueryRepository {

}
