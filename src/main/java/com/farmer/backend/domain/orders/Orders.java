package com.farmer.backend.domain.orders;

import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.delivery.Delivery;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Column(length = 20)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Long orderPrice;
    private String payMethod;
    private Integer totalQuantity;
    @Column(length = 50)
    private String comment;

    @NotNull
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime createdDate;

    public void orderStatusUpdateAction(String orderStatus) {
        this.orderStatus = OrderStatus.valueOf(orderStatus);
    }
}
