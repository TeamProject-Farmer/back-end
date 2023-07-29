package com.farmer.backend.domain.news;

public enum NewsExposure {

    ACTIVATE("활성화"), DISABLED("비활성화");

    private String name;

    NewsExposure(String name) {
        this.name = name;
    }


}
