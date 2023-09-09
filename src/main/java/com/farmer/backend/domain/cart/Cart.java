package com.farmer.backend.domain.cart;

import com.farmer.backend.domain.BaseTimeEntity;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.options.Options;
import com.farmer.backend.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "option_id")
    private Options options;

    private Integer count;

    public void cartProductQuantityUpdate(Integer beforeCount) {
        this.count = beforeCount;
    }
}
