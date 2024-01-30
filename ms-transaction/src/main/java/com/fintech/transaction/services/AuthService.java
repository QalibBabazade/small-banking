package com.fintech.transaction.services;

import com.fintech.transaction.client.AuthClient;
import com.fintech.transaction.dto.request.AuthRequest;
import com.fintech.transaction.dto.response.AuthResponse;
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
