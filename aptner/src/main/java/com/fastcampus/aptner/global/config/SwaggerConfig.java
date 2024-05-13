package com.fastcampus.aptner.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Arrays.asList(securityRequirement))
                .info(new Info()
                        .title("파이널 6조")
                        .description("<h3>아파트너 V2 단지 홈페이지 리뉴얼<h3>")
                        .version("1.0.0"));
    }

    @Bean
    public GroupedOpenApi group() {
        return GroupedOpenApi.builder()
                .group("전체")
                .pathsToMatch("/api/**")
                .build();
    }
    @Bean
    public GroupedOpenApi postGroup() {
        return GroupedOpenApi.builder()
                .group("게시글 전체")
                .pathsToMatch("/api/post/**")
                .build();
    }

    @Bean
    public GroupedOpenApi announcementGroup() {
        return GroupedOpenApi.builder()
                .group("공지사항")
                .pathsToMatch("/api/post/announcement/**")
                .build();
    }

    @Bean
    public GroupedOpenApi commentGroup() {
        return GroupedOpenApi.builder()
                .group("댓글")
                .pathsToMatch("/api/post/comment/**")
                .build();
    }

}