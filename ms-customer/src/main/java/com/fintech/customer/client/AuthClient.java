package com.fintech.customer.client;

import com.fintech.customer.dto.request.AuthRequest;
import com.fintech.customer.dto.response.AuthResponse;
import com.fintech.customer.exceptions.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


@Component
@RequiredArgsConstructor
public class AuthClient {

    private final RestTemplate restTemplate;

    @Value("${client.ms-user.auth.url}")
    private String authUserDetailsApiUrl;

    public AuthResponse getUserDetailsByJwtToken(AuthRequest request) {
        try {
            return restTemplate.postForObject(authUserDetailsApiUrl, new HttpEntity<>(request), AuthResponse.class);
        } catch (HttpStatusCodeException ex) {
            throw new BaseException(ex.getStatusCode().value(), ex.getMessage());
        }
    }
}
