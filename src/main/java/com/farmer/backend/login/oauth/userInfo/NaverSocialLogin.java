package com.farmer.backend.login.oauth.userInfo;

import com.farmer.backend.dto.user.login.OAuthUserInfoDto;
import com.farmer.backend.entity.Member;
import com.farmer.backend.entity.SocialType;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.jwt.JwtService;
import com.farmer.backend.repository.admin.member.MemberRepository;
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
public class NaverSocialLogin implements OAuthLogin{

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;
    Member naverUser;
    String accessToken = "";
    String refreshToken = "";
    SocialType socialType = SocialType.NAVER;
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
            body.add("client_secret",clientSecret);
            body.add("redirect_uri", redirectUri);
            body.add("code", code);

            RestTemplate rt = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = rt.exchange(
                    "https://nid.naver.com/oauth2.0/token",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject tokenResponse = (JSONObject) jsonParser.parse(response.getBody());

            accessToken = (String) tokenResponse.get("access_token");

        } catch (ParseException e) {
            throw new CustomException(ErrorCode.NAVER_LOGIN_FAILURE);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.NAVER_LOGIN_FAILURE);
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
                    "https://openapi.naver.com/v1/nid/me",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
            JSONObject account = (JSONObject) jsonObj.get("response");

            socialId = String.valueOf(account.get("id"));
            email = String.valueOf(account.get("email"));
            accessToken = jwtService.createAccessToken(email);
            refreshToken = jwtService.createRefreshToken();

            OAuthUserInfoDto userInfo = new OAuthUserInfoDto(socialId, socialType, email, accessToken, refreshToken);
            naverUser = userSave(userInfo);

        } catch (ParseException e) {
            throw new CustomException(ErrorCode.NAVER_LOGIN_FAILURE);
        }catch (Exception e) {
            throw new CustomException(ErrorCode.NAVER_LOGIN_FAILURE);
        }

        return OAuthUserInfoDto.getUserInfo(naverUser);
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
