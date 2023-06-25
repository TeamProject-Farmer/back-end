package com.farmer.backend.config;


import com.farmer.backend.jwt.JwtAuthenticationProcessingFilter;
import com.farmer.backend.jwt.JwtService;
import com.farmer.backend.login.CustomLoginFilter;
import com.farmer.backend.login.LoginService;
import com.farmer.backend.login.handler.LoginFailureHandler;
import com.farmer.backend.login.handler.LoginSuccessHandler;
import com.farmer.backend.oauth.CustomOAuth2UserService;
import com.farmer.backend.oauth.handler.OAuth2LoginFailureHandler;
import com.farmer.backend.oauth.handler.OAuth2LoginSuccessHandler;
import com.farmer.backend.repository.admin.member.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig  {

    private final LoginService loginService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().antMatchers(
                "/v3/api-docs",
                "/swagger-ui/**"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/v3/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
                .authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorization")
                .and()
                .redirectionEndpoint().baseUri("/login/*")
                .and()
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandler);
//                .antMatchers("/member").permitAll()
//                .antMatchers("/member/mail").permitAll()
//                .antMatchers("/member/join").permitAll()
//                .antMatchers("/api/admin/**").permitAll()
//                .anyRequest().authenticated();

        http.addFilterAfter(customLoginFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), CustomLoginFilter.class);
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CustomLoginFilter customLoginFilter() {
        CustomLoginFilter customLoginFilter
                = new CustomLoginFilter(objectMapper);

        customLoginFilter.setAuthenticationManager(authenticationManager());
        customLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customLoginFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService,objectMapper,memberRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationFilter() {
        JwtAuthenticationProcessingFilter jwtAuthenticationFilter
                = new JwtAuthenticationProcessingFilter(jwtService, memberRepository);

        return jwtAuthenticationFilter;
    }




}
