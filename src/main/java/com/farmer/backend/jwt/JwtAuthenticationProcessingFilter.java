package com.farmer.backend.jwt;

import com.farmer.backend.domain.member.Member;
import com.farmer.backend.domain.member.MemberRepository;
import com.farmer.backend.login.general.MemberAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private static final String MAIN_URL = "/api/main";
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{

        if(request.getRequestURI().contains(MAIN_URL)){
            filterChain.doFilter(request,response);
            return;
        }

        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(refresh -> jwtService.isTokenValid("refresh",refresh,response))
                .orElse(null);

        if (refreshToken != null){
            checkRefreshTokenAndReIssueAccessToken(response,refreshToken);
            return;
        }

        if (refreshToken == null){

            checkAccessTokenAndAuthentication(request, response, filterChain);
        }
    }

    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) throws IOException {

        Member member= memberRepository.findByRefreshToken(refreshToken).orElseThrow(()-> new NullPointerException("회원이 존재하지 않습니다."));

        String reIssueRefreshToken = jwtService.createRefreshToken();
        String reIssueAccessToken= jwtService.createAccessToken(member.getEmail());

        member.updateToken(reIssueRefreshToken,reIssueAccessToken);
        memberRepository.saveAndFlush(member);

        jwtService.sendAccessAndRefreshToken(member,response, reIssueAccessToken,reIssueRefreshToken);


    }


    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {

        jwtService.extractAccessToken(request)
                .filter(token -> jwtService.isTokenValid("access",token,response))
                .ifPresentOrElse(
                        accessToken -> {
                            jwtService.extractUserEmail(accessToken)
                                    .ifPresent(email -> memberRepository.findByEmail(email)
                                            .ifPresent(this::saveAuthentication));},
                        ()-> {throw new NullPointerException("토큰을 다시 확인해주세요");
                        });

        filterChain.doFilter(request,response);
    }

    public void saveAuthentication(Member myUser) {

        String password = myUser.getPassword();

        MemberAdapter memberAdapter = new MemberAdapter(myUser);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(memberAdapter, password,
                        authoritiesMapper.mapAuthorities(memberAdapter.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}

