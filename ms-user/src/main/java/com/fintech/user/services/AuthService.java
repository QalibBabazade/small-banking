package com.fintech.user.services;

import com.fintech.user.config.JwtTokenProvider;
import com.fintech.user.dto.request.AuthTokenRequest;
import com.fintech.user.dto.response.AuthResponse;
import com.fintech.user.enums.ErrorCodes;
import com.fintech.user.exceptions.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse getUserDetails(AuthTokenRequest request) {

        String jwtToken = request.getJwtToken();
        if (!jwtTokenProvider.validateToken(jwtToken)) {
            throw BaseException.of(ErrorCodes.INVALID_ARGUMENTS);
        }
        String pin = jwtTokenProvider.getPinFromJWT(jwtToken);
        return new AuthResponse(Boolean.TRUE, pin);
    }
}
