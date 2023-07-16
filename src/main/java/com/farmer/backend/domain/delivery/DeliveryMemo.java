package com.farmer.backend.domain.delivery;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryMemo {

    OFFICE("부재 시 경비실에 맡겨주세요"),
    BOX("부재 시 택배함에 넣어주세요"),
    FRONT("부재 시 집 앞에 놔주세요"),
    CALL("배송 전 연락 바랍니다."),
    TEXT("직접 입력");


    public String getName() {
        return name;
    }

    private String name;
}
