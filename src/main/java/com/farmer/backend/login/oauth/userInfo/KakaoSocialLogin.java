package com.farmer.backend.login.oauth.userInfo;

import com.farmer.backend.api.controller.user.login.OAuthUserInfoDto;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.SocialType;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.jwt.JwtService;
import com.farmer.backend.domain.member.MemberRepository;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Getter
@Component
@RequiredArgsConstructor
public class KakaoSocialLogin implements OAuthLogin {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    Member kakaoUser;
    String accessToken = "";
    String refreshToken = "";
    SocialType socialType = SocialType.KAKAO;
    String socialId = "";
    String email = "";

    /**
     * 인가 코드를 통해 AccessToken 얻기
     */
    @Override
    public String getAccessToken(String code) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

            body.add("grant_type", "authorization_code");
            body.add("client_id", clientId);
            body.add("redirect_uri", redirectUri);
            body.add("code", code);

            RestTemplate rt = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = rt.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

            accessToken = (String) jsonObj.get("access_token");
            refreshToken = (String) jsonObj.get("refresh_token");


        } catch (ParseException e) {
            throw new CustomException(ErrorCode.KAKAO_LOGIN_FAILURE);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.KAKAO_LOGIN_FAILURE);
        }

        return accessToken;
    }

    /**
     * AccessToken으로 사용자 정보 얻기
     */
    @Override
    public OAuthUserInfoDto getUserInfo(String code) {
        accessToken = getAccessToken(code);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            //HttpHeader 담기
            RestTemplate rt = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> response = rt.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
            JSONObject account = (JSONObject) jsonObj.get("kakao_account");

            socialId = String.valueOf(jsonObj.get("id"));
            email = String.valueOf(account.get("email"));
            accessToken = jwtService.createAccessToken(email);
            refreshToken = jwtService.createRefreshToken();

            OAuthUserInfoDto userInfo = new OAuthUserInfoDto(socialId, socialType, email, accessToken, refreshToken);
            kakaoUser = userSave(userInfo);


        } catch (ParseException e) {
            throw new CustomException(ErrorCode.KAKAO_LOGIN_FAILURE);
        }catch (Exception e) {
            throw new CustomException(ErrorCode.KAKAO_LOGIN_FAILURE);
        }

        return OAuthUserInfoDto.getUserInfo(kakaoUser);
    }

    /**
     * 유저 정보 저장
     */
    @Override
    public Member userSave(OAuthUserInfoDto userInfo) {

        Member member = userInfo.toEntity(userInfo);

        if (!memberRepository.findBySocialId(member.getSocialId()).isPresent()) {
            memberRepository.save(member);
        }
        return member;
    }
}
