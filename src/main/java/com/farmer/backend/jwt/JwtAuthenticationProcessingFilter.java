package com.farmer.backend.jwt;

import com.farmer.backend.entity.Member;
import com.farmer.backend.repository.admin.member.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.print.DocFlavor;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL ="/api/member/login/*";

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{

        if(!request.getRequestURI().equals(NO_CHECK_URL)){
            filterChain.doFilter(request,response);
            return;
        }

        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        //refreshtoken이 헤더에 존재했다면 , db의 refreshtoken과 일치하는지 판단 후, 일치하면 accesstoken 발급
        if (refreshToken != null){
            checkRefreshTokenAndReIssueAccessToken(response,refreshToken);
            return;
        }

        // refreshtoken이 없거나 유효하지 않다면 accesstoken을 검사하고 인증을 처리하는 로직 수행
        // accesstoken이 없거나 유효하지 않다면, 인증 객체가 담기지 않은 상태로 다음 필터로 넘어가기 때문에 에러 발생
        // accesstoken이 유효하면, 인증 객체가 담긴 상태로 다음 필터로 넘어가 인증 성공
        if (refreshToken == null){
            checkAccessTokenAndAuthentication(request, response, filterChain);
        }
    }

    // refreshtoken을 통해 db에서 유저를 찾고, accesstoken을 재발급 해주는 메소드
    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken){
        memberRepository.findByRefreshToken(refreshToken)
                .ifPresent(member -> {
                    String reIssueRefreshToken = reIssueRereshToken(member);
                    jwtService.sendAccessAndRefreshToken(response,jwtService.createAccessToken(member.getEmail()),
                            reIssueRefreshToken);
                });
    }

    //refreshtoken 재발급 & db에 refreshtoken 업데이트 메소드
    private String reIssueRereshToken(Member member){
        String reIssuedRefreshToken = jwtService.createRefreshToken();
        member.updateRefreshToken(reIssuedRefreshToken);
        memberRepository.saveAndFlush(member);
        return reIssuedRefreshToken;
    }

    //accesstoken의 유효성을 검증 & 인증 처리 메소드
    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");

        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken -> jwtService.extractuserid(accessToken)
                        .ifPresent(email -> memberRepository.findByEmail(email)
                                .ifPresent(this::saveAuthentication)));

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(Member myUser) {

        log.info("saveAuthentication() 호출");
        String password = myUser.getPassword();
        if (password == null) { // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정
            password = PasswordUtil.generateRandomPassword();
        }
        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getEmail())
                .password(password)
                .roles(myUser.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}

