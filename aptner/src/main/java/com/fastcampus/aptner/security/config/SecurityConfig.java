package com.fastcampus.aptner.security.config;

import com.fastcampus.aptner.jwt.exception.CustomAuthenticationEntryPoint;
import com.fastcampus.aptner.jwt.filter.JwtAuthenticationFilter;
import com.fastcampus.aptner.jwt.provider.JwtAuthenticationProvider;
import com.fastcampus.aptner.security.util.CustomResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));

        // cors 설정 등록
        http.cors(httpSecurityCorsConfigurer ->
                httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));

        // session STATELESS 비활성화
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // csrf 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // 폼 로그인 비활성화 -> 소셜 로그인 X, SPA(React + Next.js) 으로 개발하기 때문에 폼 로그인 비활성화
        http.formLogin(AbstractHttpConfigurer::disable);

        // iframe 비활성화
        http.headers(httpSecurityHeadersConfigurer ->
                httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        // HTTP Basic 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable);

        // 회원 인증 실패 401
        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) -> {
                    CustomResponseUtil.fail(response, "로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
                }));

        // 회원 권한 실패 403
        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                httpSecurityExceptionHandlingConfigurer.accessDeniedHandler((request, response, accessDeniedException) -> {
                    CustomResponseUtil.fail(response, "권한이 없습니다.", HttpStatus.FORBIDDEN);
                }));

        // TODO: 임시로 모든 요청에 허용한 상태이다. -> 게시판 추가가 필요하다.
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // Preflight 요청은 허용
                                .requestMatchers("/api/member/**").permitAll()
                                .requestMatchers("/api/apartment/**").permitAll()
                                .requestMatchers("/api/update/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                                .requestMatchers("/swagger/**","/api-docs/**", "/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**", "/").permitAll()
                                .anyRequest().authenticated()
                );

        // TODO: JWT 인증 필터 주석 작성하기.
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(jwtAuthenticationProvider);

        return http.build();
    }

    // 스프링 Ioc 컨테이너에 BCryptPasswordEncoder() 객체를 등록한다.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(List.of("*")); // 모든 허용(React + Next.js)
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    /**
     * 보안 필터 예외 처리
     *
     * @return 모든 정적 리소스는 보안 요청을 무시
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // TODO:authenticationManager 주석 작성하기
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
