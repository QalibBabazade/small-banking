package com.fintech.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.user.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException{

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write
                (
                        new ObjectMapper().writeValueAsString(ErrorResponse.builder()
                                .traceId(UUID.randomUUID().toString())
                                .errorCode(HttpStatus.UNAUTHORIZED.value())
                                .description("Denied access for resource!")
                                .build())
                );
    }
}
