package com.fastcampus.aptner.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("파이널 6조")
                        .description("<h3>아파트너 V2 단지 홈페이지 리뉴얼<h3>")
                        .version("1.0.0"));
    }

}