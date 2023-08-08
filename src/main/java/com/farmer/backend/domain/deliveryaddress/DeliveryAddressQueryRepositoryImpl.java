package com.farmer.backend.domain.deliveryaddress;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class DeliveryAddressQueryRepositoryImpl implements DeliveryAddressQueryRepository{

    private final JPAQueryFactory query;

    public DeliveryAddressQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


}
