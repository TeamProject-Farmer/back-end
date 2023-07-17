package com.farmer.backend.domain.cart;

import com.farmer.backend.api.controller.cart.request.RequestCartProductQuantityDto;
import com.farmer.backend.api.controller.cart.response.ResponseCartProductListDto;
import com.farmer.backend.api.controller.cart.response.ResponseCartProductQuantityDto;
import com.farmer.backend.domain.member.Member;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.farmer.backend.domain.cart.QCart.cart;

@Repository
public class CartQueryRepositoryImpl implements CartQueryRepository{

    private final JPAQueryFactory query;

    public CartQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


    @Override
    public List<ResponseCartProductListDto> findCartProductListByMember(Member findMember) {
        return query
                .select(Projections.constructor(
                        ResponseCartProductListDto.class,
                        cart.product.id,
                        cart.product.thumbnailImg,
                        cart.product.name,
                        cart.options.id,
                        cart.options.optionName,
                        cart.count,
                        cart.product.price,
                        cart.product.price.multiply(cart.count)
                ))
                .from(cart)
                .fetch();

    }

    @Override
    public ResponseCartProductQuantityDto findCartProductByCartId(Long cartId) {
        return query
                .select(Projections.constructor(
                        ResponseCartProductQuantityDto.class,
                        cart.product.id,
                        cart.count,
                        cart.product.price,
                        cart.product.price.multiply(cart.count)
                ))
                .from(cart)
                .where(cart.id.eq(cartId))
                .fetchFirst();
    }
}
