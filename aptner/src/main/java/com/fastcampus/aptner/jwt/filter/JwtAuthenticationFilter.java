package com.fastcampus.aptner.jwt.filter;

import com.fastcampus.aptner.jwt.exception.JwtExceptionCode;
import com.fastcampus.aptner.jwt.token.JwtAuthenticationToken;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token;
        try {
            token = getToken(request);
            if (StringUtils.hasText(token)) {
                System.out.println("-------------------------------token = " + token);
                getAuthentication(token);
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token has expired. Please refresh your token.");

        } catch (NullPointerException | IllegalStateException e) {

            request.setAttribute("doFilterInternal: exception", JwtExceptionCode.NOT_FOUND_TOKEN.getCode());
            throw new BadCredentialsException("doFilterInternal: throw new not found token exception");

        } catch (SecurityException | MalformedJwtException e) {

            request.setAttribute("doFilterInternal: exception", JwtExceptionCode.INVALID_TOKEN.getCode());
            throw new BadCredentialsException("doFilterInternal: throw new invalid token exception");

        } catch (UnsupportedJwtException e) {

            request.setAttribute("doFilterInternal: exception", JwtExceptionCode.UNSUPPORTED_TOKEN.getCode());
            throw new BadCredentialsException("throw new unsupported token exception");

        } catch (Exception e) {

            throw new BadCredentialsException("throw new exception");

        }
    }

    private void getAuthentication(String token) {
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(token);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer")){
            String[] arr = authorization.split(" ");
            return arr[1];
        }
        return null;
    }
}

