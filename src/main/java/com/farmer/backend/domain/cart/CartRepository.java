package com.farmer.backend.domain.cart;

import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long>, CartQueryRepository {

    Optional<Cart> findByProductAndMember(Product product, Member findMember);
}
