package com.farmer.backend.config;


import com.farmer.backend.jwt.JwtAuthenticationProcessingFilter;
import com.farmer.backend.jwt.JwtService;
import com.farmer.backend.login.CustomAuthenticationFilter;
import com.farmer.backend.login.LoginService;
import com.farmer.backend.login.handler.LoginFailureHandler;
import com.farmer.backend.login.handler.LoginSuccessHandler;
import com.farmer.backend.repository.admin.member.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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
@EnableWebSecurity // spring security 설정을 활성화시켜주는 어노테이션
public class SecurityConfig  {

    private final LoginService loginService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

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

                .formLogin().disable()//로그인 화면 페이지 ?
                .httpBasic().disable() //로그인 인증창
                .csrf().disable()// 서버에 인증 정보를 저장하지 않기 떄문에 csrf 코드들을 작성할 필요없음
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//세션 관리 기능이 작동하는 정책으로, stateless를 사용하면, 스프링 시큐리티가 세션을 생성하지 않고, 존재해도 사용하지 않는다는 정책으로 , JWT 웹 토큰을 이용해 토큰에 사용자의 정보를 저장하고 인증하는 방식
                .and()
                .cors()
                .and()
                .authorizeRequests()//특정 리소스의 접근 허용 또는 특정 권한을 가진 사용자만 접근 가능하도록 설정
                .antMatchers("/v3/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
                .authenticated();
                //.antMatchers("/member/login/oauth2/code/kakao").permitAll()
//                .antMatchers("/member").permitAll()
//                .antMatchers("/member/mail").permitAll()
//                .antMatchers("/member/join").permitAll() //permitall() -> 리소스의 접근을 인증절차 없이 허용
//                .antMatchers("/api/admin/**").permitAll()
//                .antMatchers("/v3/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
//                .anyRequest().authenticated();// 나머지는 인증된 사용자의 접근만 허용
//               .and()
//               .oauth2Login();
//               .successHandler(oAuth2LoginSuccessHandler) // 동의하고 계속하기를 눌렀을 때 Handler 설정
//               .failureHandler(oAuth2LoginFailureHandler) // 소셜 로그인 실패 시 핸들러 설정
//               .userInfoEndpoint().userService(customOAuth2MemberService); // customUserService 설정


        http.addFilterAfter(customAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), CustomAuthenticationFilter.class);
     return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
        return new LoginSuccessHandler(jwtService, memberRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }


    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() {
        CustomAuthenticationFilter customLoginFilter
                = new CustomAuthenticationFilter(objectMapper);
        customLoginFilter.setAuthenticationManager(authenticationManager());
        customLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customLoginFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationFilter() {
        JwtAuthenticationProcessingFilter jwtAuthenticationFilter
                = new JwtAuthenticationProcessingFilter(jwtService, memberRepository);
        return jwtAuthenticationFilter;
    }



}
