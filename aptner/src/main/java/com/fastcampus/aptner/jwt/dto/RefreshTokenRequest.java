package com.fastcampus.aptner.jwt.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "리프레시 토큰 재발행 Body",description = "리프레시 토큰 재발행 요청 Body")
@Data
public class RefreshTokenRequest {

    @Schema(description = "이전 리프레시 토큰 입력(필수)")
    String refreshToken;
}
