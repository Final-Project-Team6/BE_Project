package com.fastcampus.aptner.security.util;

import com.fastcampus.aptner.member.dto.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class CustomResponseUtil {

    private static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

    public static void signInSuccess(HttpServletResponse response, Object dto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HttpResponse<?> memberResponse = new HttpResponse<>(1, "로그인에 성공했습니다.", dto);
            String responseBody = objectMapper.writeValueAsString(memberResponse);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("signInSuccess: 회원 로그인 정보 파싱 실패");
        }
    }

    public static void fail(HttpServletResponse response, String message, HttpStatus httpStatus) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HttpResponse<?> memberResponse = new HttpResponse<>(-1, message, null);
            String responseBody = objectMapper.writeValueAsString(memberResponse);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(httpStatus.value());
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("fail: 파싱 실패");
        }
    }

}
