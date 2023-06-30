package com.farmer.backend.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductDivision {

    NORMAL("일반"), MD_PICK("MD_PICK");

    private String name;
}
