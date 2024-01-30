package com.fintech.user.controller;

import com.fintech.user.dto.request.UserRequest;
import com.fintech.user.dto.response.CommonResponse;
import com.fintech.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public CommonResponse<String> signUp(@RequestBody @Valid UserRequest request) {
        return userService.signUp(request);

    }

    @PostMapping("/sign-in")
    public CommonResponse<String> signIn(@RequestBody @Valid UserRequest request) {
        return userService.signIn(request);
    }


}
