package com.farmer.backend.domain.payment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.farmer.backend.domain.payment.QPayment.payment;


@Repository
public class PaymentQueryRepositoryImpl implements PaymentQueryRepository {

    private final JPAQueryFactory query;

    public PaymentQueryRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }


    @Override
    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return query
                .selectFrom(payment)
                .where(payment.orders.id.eq(orderId))
                .fetch();
    }
}
