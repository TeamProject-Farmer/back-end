package com.farmer.backend.login.general.handler;

import com.farmer.backend.api.controller.user.login.ResponseLoginMemberDto;
import com.farmer.backend.domain.member.Member;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.jwt.JwtService;
import com.farmer.backend.domain.member.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;

    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String email = authentication.getName();
        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        member.updateToken(refreshToken,accessToken);
        memberRepository.saveAndFlush(member);

        ResponseLoginMemberDto loginMemberDto = ResponseLoginMemberDto.getLoginMember(member);

        String res = objectMapper.writeValueAsString(loginMemberDto);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(res);
    }


}
