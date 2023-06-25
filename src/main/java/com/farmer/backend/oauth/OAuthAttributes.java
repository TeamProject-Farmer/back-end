package com.farmer.backend.oauth;

import com.farmer.backend.entity.*;
import com.farmer.backend.oauth.info.GoogleOAuth2UserInfo;
import com.farmer.backend.oauth.info.KakaoOAuth2UserInfo;
import com.farmer.backend.oauth.info.NaverOAuth2UserInfo;
import com.farmer.backend.oauth.info.OAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class OAuthAttributes {

    private String nameAttributeKey;
    private OAuth2UserInfo oauth2UserInfo;

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }


    public static OAuthAttributes of(SocialType socialType,
                                     String userNameAttributeName, Map<String, Object> attributes) {

        if (socialType == SocialType.NAVER) {
            return ofNaver(userNameAttributeName, attributes);
        }
        if (socialType == SocialType.KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
       }
        return ofGoogle(userNameAttributeName, attributes);
   }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }

    public Member toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
        return Member.builder()
                .email(oauth2UserInfo.getEmail())
                .password(String.valueOf(UUID.randomUUID()))
                .username(oauth2UserInfo.getNickname())
                .point(0L)
                .grade(Grade.NORMAL)
                .accountStatus(AccountStatus.ACTIVITY)
                .cumulativeAmount(0L)
                .socialType(socialType)
                .socialId(oauth2UserInfo.getId())
                .nickname(oauth2UserInfo.getNickname())
                .role(UserRole.USER)
                .build();
    }
}