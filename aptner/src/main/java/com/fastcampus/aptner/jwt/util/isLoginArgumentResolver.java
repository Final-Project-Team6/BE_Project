package com.fastcampus.aptner.jwt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class isLoginArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger logger = LoggerFactory.getLogger(isLoginArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(isLogin.class) != null
                && parameter.getParameterType() == JWTMemberInfoDTO.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            logger.error("Authentication is null");
            return null;
        }

        if (!(authentication.getPrincipal() instanceof JWTMemberInfoDTO)) {
            logger.error("Principal is not of type MemberInfoRequest");
            return null;
        }

        JWTMemberInfoDTO JWTMemberInfoDTO = (JWTMemberInfoDTO) authentication.getPrincipal();
        logger.info("Resolved MemberInfoRequest: {}", JWTMemberInfoDTO);

        return JWTMemberInfoDTO;
    }
}