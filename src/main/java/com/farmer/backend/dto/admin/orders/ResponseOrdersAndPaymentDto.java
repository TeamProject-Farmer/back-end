package com.farmer.backend.dto.admin.orders;

import com.farmer.backend.entity.DeliveryStatus;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ResponseOrdersAndPaymentDto {

    // 주문 회원 정보
    private Long orderId; //주문 일련번호
    private String orderNum; //주문번호
    private String memberName; //회원이름
    private String hp; //회원 전화번호
    private String email; //회원 이메일(계정 아이디)

    // 주문 배송 정보
    private String address; //배송지
    private DeliveryStatus deliveryStatus; //배송상태
    private String deliveryMemo; //배송메모

    //주문 결제 정보
    private Long totalPrice; //주문금액
    private int discountPrice; //할인금액
    private int deliveryPrice; //배송비
    private int usePointPrice; //적립금 사용금액
    private int addPoint; //추가 적립금액
    private String payMethod; //결제방식
    private Long finalAmount; //최종 결제금액

    public ResponseOrdersAndPaymentDto(Long orderId, String orderNum, String memberName, String hp, String email, String address, DeliveryStatus deliveryStatus, String deliveryMemo, Long totalPrice, int discountPrice, int deliveryPrice, int usePointPrice, int addPoint, String payMethod, Long finalAmount) {
        this.orderId = orderId;
        this.orderNum = orderNum;
        this.memberName = memberName;
        this.hp = hp;
        this.email = email;
        this.address = address;
        this.deliveryStatus = deliveryStatus;
        this.deliveryMemo = deliveryMemo;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.deliveryPrice = deliveryPrice;
        this.usePointPrice = usePointPrice;
        this.addPoint = addPoint;
        this.payMethod = payMethod;
        this.finalAmount = finalAmount;
    }



}
