package com.fintech.customer.services;

import com.fintech.customer.client.AuthClient;
import com.fintech.customer.dto.request.AuthRequest;
import com.fintech.customer.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthClient authClient;

    public AuthResponse getUserDetailsByJwtToken(String token) {
        return authClient.getUserDetailsByJwtToken(new AuthRequest(token));
    }
}
