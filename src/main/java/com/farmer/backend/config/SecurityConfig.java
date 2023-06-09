package com.farmer.backend.config;


import com.farmer.backend.domain.memberscoupon.MemberCouponRepository;
import com.farmer.backend.jwt.JwtAuthenticationProcessingFilter;
import com.farmer.backend.jwt.JwtService;
import com.farmer.backend.login.general.CustomLoginFilter;
import com.farmer.backend.login.general.LoginService;
import com.farmer.backend.login.general.handler.LoginFailureHandler;
import com.farmer.backend.login.general.handler.LoginSuccessHandler;
import com.farmer.backend.domain.member.MemberRepository;
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
    private final MemberCouponRepository memberCouponRepository;

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
                .authenticated();
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
        return new LoginSuccessHandler(jwtService,objectMapper,memberRepository,memberCouponRepository);
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
