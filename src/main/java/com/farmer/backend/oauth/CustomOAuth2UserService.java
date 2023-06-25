package com.farmer.backend.oauth;

import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.SocialType;
import com.farmer.backend.entity.UserRole;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.jwt.JwtService;
import com.farmer.backend.oauth.handler.OAuth2LoginFailureHandler;
import com.farmer.backend.repository.admin.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    private static final String NAVER = "naver";
    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("OAuth2 로그인 요청 실행");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> newOAuth = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = newOAuth.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);

        String oauth2Key = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> userInfo = oAuth2User.getAttributes();
        OAuthAttributes socialMember = OAuthAttributes.of(socialType, oauth2Key, userInfo);

        if (!memberRepository.findBySocialId(socialMember.getOauth2UserInfo().getId()).isPresent()){
            Member member= socialMember.toEntity(socialType,socialMember.getOauth2UserInfo());
            memberRepository.save(member);
        }

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(UserRole.USER.name())),
                userInfo,
                socialMember.getNameAttributeKey(),
                socialMember.getOauth2UserInfo().getEmail(),
                socialType,
                socialMember.getOauth2UserInfo().getId()
        );
    }

    private SocialType getSocialType(String registrationId) {

        if(NAVER.equals(registrationId)) {
            return SocialType.NAVER;
        }
        if(KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        return SocialType.GOOGLE;
    }

}