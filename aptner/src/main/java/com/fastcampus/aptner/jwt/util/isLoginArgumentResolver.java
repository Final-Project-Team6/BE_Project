package com.fastcampus.aptner.jwt.util;

import com.fastcampus.aptner.jwt.token.JwtAuthenticationToken;
import com.fastcampus.aptner.member.dto.reqeust.SignInMemberRequest;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collection;
import java.util.Iterator;

public class isLoginArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(isLogin.class) != null
                && parameter.getParameterType() == SignInMemberRequest.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = null;
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
        } catch (Exception ex) {
            return null;
        }
        if (authentication == null) {
            return null;
        }

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken)authentication;
        MemberLoginResponse memberLoginResponse = new MemberLoginResponse();

        Object principal = jwtAuthenticationToken.getPrincipal(); // LoginInfoDto
        if (principal == null)
            return null;

        MemberInfoRequest memberInfoRequest = (MemberInfoRequest)principal;
        memberLoginResponse.setUsername(memberInfoRequest.getUsername());
        memberLoginResponse.setMemberId(memberInfoRequest.getMemberId());
        memberLoginResponse.setApartmentName(memberInfoRequest.getApartmentName());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();

        while (iterator.hasNext()) {
            GrantedAuthority grantedAuthority = iterator.next();
            String role = grantedAuthority.getAuthority();
//            System.out.println(role);
            memberLoginResponse.setRoleName(role);
        }

        return memberLoginResponse;
    }
}
