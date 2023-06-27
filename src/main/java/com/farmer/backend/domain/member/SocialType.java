package com.farmer.backend.domain.member;

public enum SocialType {
    KAKAO("kakao"), NAVER("naver"), GOOGLE("google") ;

    private String name;

    SocialType(String name) {
        this.name = name;
    }

}
