package com.farmer.backend.dto.admin.orders;

import com.farmer.backend.entity.Delivery;
import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.OrderStatus;
import com.sun.istack.NotNull;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ResponseOrdersDto {

    private Long id;
    private Member member;
    private Delivery delivery;
    private OrderStatus orderStatus;
    private Long orderPrice;
    private String payMethod;
    private Integer totalQuantity;
    private String comment;
    private LocalDateTime createdDate;
}
