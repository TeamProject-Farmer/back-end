package com.farmer.backend.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String LOGIN_REQUEST_URL = "/api/member/login";
    private static final String HTTP_METHOD = "POST";
    private final ObjectMapper objectMapper;
    private static final AntPathRequestMatcher LOGIN_REQUEST_MATCHER =
            new AntPathRequestMatcher(LOGIN_REQUEST_URL, HTTP_METHOD);

    public CustomLoginFilter(ObjectMapper objectMapper) {
        super(LOGIN_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
