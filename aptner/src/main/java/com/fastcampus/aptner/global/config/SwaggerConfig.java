package com.fastcampus.aptner.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
        Server server = new Server();
        server.setUrl("https://aptner.shop");
        Server local = new Server();
        local.setUrl("http://localhost:8080");
        List<Server> serverList = new ArrayList<>();
        serverList.add(server);
        serverList.add(local);
        return new OpenAPI()
                .servers(serverList)
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
                .pathsToMatch("/api/post/**","/api/admin/post/**")
                .build();
    }

    @Bean
    public GroupedOpenApi announcementGroup() {
        return GroupedOpenApi.builder()
                .group("공지사항")
                .pathsToMatch("/api/post/announcement/**","/api/admin/post/announcement/**")
                .build();
    }

    @Bean
    public GroupedOpenApi commentGroup() {
        return GroupedOpenApi.builder()
                .group("댓글, 공감")
                .pathsToMatch("/api/post/comment/**","/api/post/vote/**")
                .build();
    }

    @Bean
    public GroupedOpenApi complaintGroup() {
        return GroupedOpenApi.builder()
                .group("민원 게시판")
                .pathsToMatch("/api/post/complaint/**","/api/admin/post/complaint/**")
                .build();
    }

    @Bean
    public GroupedOpenApi joinGroup() {
        return GroupedOpenApi.builder()
                .group("회원")
                .pathsToMatch("/api/member/**")
                .build();
    }


    @Bean
    public GroupedOpenApi refreshTokenGroup() {
        return GroupedOpenApi.builder()
                .group("JWT 리프레시 토큰")
                .pathsToMatch("/api/refresh-token/**")
                .build();
    }

    @Bean
    public GroupedOpenApi apartmentCreateGroup() {
        return GroupedOpenApi.builder()
                .group("아파트")
                .pathsToMatch("/api/apartment/**")
                .build();
    }

    @Bean
    public GroupedOpenApi communicationGroup() {
        return GroupedOpenApi.builder()
                .group("소통")
                .pathsToMatch("/api/post/communication/**")
                .build();
    }

    @Bean
    public GroupedOpenApi informationGroup() {
        return GroupedOpenApi.builder()
                .group("정보")
                .pathsToMatch("/api/post/information/**")
                .build();
    }
}