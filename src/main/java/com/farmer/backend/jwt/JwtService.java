package com.farmer.backend.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.farmer.backend.api.controller.login.ResponseLoginMemberDto;
import com.farmer.backend.api.controller.member.response.ResponseMemberTokenDto;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.domain.memberscoupon.MemberCouponRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static javax.servlet.http.HttpServletResponse.*;
@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessExpiration;

    @Value("${jwt.refresh.expiration}")
    private Long refreshExpiration;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN = "AccessToken";
    private static final String REFRESH_TOKEN = "RefreshToken";
    private static final String USERID_CLAIM = "account_email";
    private static final String BEARER = "Bearer ";
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;
    private final MemberCouponRepository memberCouponRepository;


    /**
     * AccessToken 생성
     * @param email 이메일
     */
    public String createAccessToken(String email) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN)
                .withExpiresAt(new Date(now.getTime() + accessExpiration)) //토큰 만료시간 설정
                .withClaim(USERID_CLAIM, email)
                .sign(Algorithm.HMAC512(secretKey));
    }

    /**
     * RefreshToken 생성
     */
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN)
                .withExpiresAt(new Date(now.getTime() + refreshExpiration))
                .sign(Algorithm.HMAC512(secretKey));
    }


    /**
     * AccessToken , RefreshToken 보내기
     */
    public void sendAccessAndRefreshToken(Member member, HttpServletResponse response, String accessToken, String refreshToken) throws IOException {


        //ResponseMemberTokenDto responseMemberTokenDto = new ResponseMemberTokenDto(accessToken,refreshToken);
        //String memberToken = objectMapper.writeValueAsString(responseMemberTokenDto);
        //response.getWriter().write(memberToken);
        Long couponCount = memberCouponRepository.countByMemberId(member.getId());

        response.setStatus(SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ResponseLoginMemberDto loginMemberDto = ResponseLoginMemberDto.getLoginMember(member,couponCount);

        String res = objectMapper.writeValueAsString(loginMemberDto);
        response.getWriter().write(res);




    }

    /**
     * 헤더에서 RefreshToken 추출
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {

            return Optional.ofNullable(request.getHeader(refreshHeader))
                    .filter(refreshToken -> refreshToken.startsWith(BEARER))
                    .map(refreshToken -> refreshToken.replace(BEARER, ""));


    }

    /**
     * 헤더에서 AccessToken 추출
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {


            return Optional.ofNullable(request.getHeader(accessHeader))
                    .filter(accessToken -> accessToken.startsWith(BEARER))
                    .map(accessToken -> accessToken.replace(BEARER, ""));


    }

    /**
     * AccessToken에서 Email추출
     */
    public Optional<String> extractUserEmail(String accessToken) {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim(USERID_CLAIM)
                    .asString());
    }


    /**
     * 토큰 유효성 검사
     */
    @SneakyThrows
    public boolean isTokenValid(String type, String token, HttpServletResponse response){
        ObjectMapper mapper = new ObjectMapper();



        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        }
        catch (TokenExpiredException e) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            ResponseStatusException tokenExpiredException
                    = new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, type+ "토큰이 만료되었습니다.");

            mapper.writeValue(response.getWriter(),tokenExpiredException.getReason());

            throw tokenExpiredException;
        }
        catch (Exception e){

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            ResponseStatusException responseStatusException
                    = new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, type+ "토큰을 다시 확인해주세요.");

            mapper.writeValue(response.getWriter(),responseStatusException.getReason());

            throw responseStatusException;
        }
    }

}
