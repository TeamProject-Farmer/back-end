package com.farmer.backend.domain.payment;

import com.farmer.backend.api.controller.order.request.RequestOrderInfoDto;
import com.farmer.backend.domain.orders.Orders;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @NotNull
    private Long totalPrice;

    private Long discountPrice;
    private Long deliveryPrice;
    private Long usePointPrice;
    private Long addPoint;

    @NotNull
    private Long finalAmount;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime createdDate;

    public static Payment toEntity(Orders orders, RequestOrderInfoDto orderInfoDto) {
        return Payment.builder()
                .orders(orders)
                .totalPrice(orders.getOrderPrice())
                .discountPrice(orderInfoDto.getPoint())
                .deliveryPrice(3000L)
                .usePointPrice(orderInfoDto.getPoint())
                .addPoint(orders.getOrderPrice() * 3 / 100)
                .finalAmount(orders.getOrderPrice())
                .build();
    }

}
