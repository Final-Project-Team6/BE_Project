package com.fastcampus.aptner.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

// Spring MVC 에 대한 설정파일. 웹에대한 설정파일
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 프론트엔드 포트
                .allowedOrigins("http://localhost:8080") // 백엔드 포트1
                .allowedOrigins("http://localhost:8081") // 백엔드 포트2
                .allowedOrigins("https://aptner.shop")
                .allowedOrigins("https://aptner.shop/swagger-ui/index.html")
                .allowedMethods("GET", "POST", "PATCH", "PUT", "OPTIONS", "DELETE");
//                .allowCredentials(true);
    }

}
