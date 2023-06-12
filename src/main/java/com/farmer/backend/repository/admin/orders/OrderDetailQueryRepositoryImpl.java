package com.farmer.backend.repository.admin.orders;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class OrderDetailQueryRepositoryImpl implements OrderDetailQueryRepository{

    private final JPAQueryFactory query;

    public OrderDetailQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

}
