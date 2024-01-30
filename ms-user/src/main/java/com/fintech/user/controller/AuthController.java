package com.fintech.user.controller;

import com.fintech.user.dto.request.AuthTokenRequest;
import com.fintech.user.dto.response.AuthResponse;
import com.fintech.user.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/user-details")
    public AuthResponse getUserDetails(@Valid @RequestBody AuthTokenRequest request) {
        return authService.getUserDetails(request);
    }

}
