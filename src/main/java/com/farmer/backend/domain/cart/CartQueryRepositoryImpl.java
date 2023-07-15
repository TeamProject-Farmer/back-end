package com.farmer.backend.domain.cart;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CartQueryRepositoryImpl implements CartQueryRepository{

    private final JPAQueryFactory query;

    public CartQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


}
