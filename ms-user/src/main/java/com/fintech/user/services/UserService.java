package com.fintech.user.services;

import com.fintech.user.config.JwtTokenProvider;
import com.fintech.user.dto.request.UserRequest;
import com.fintech.user.dto.response.CommonResponse;
import com.fintech.user.entities.RoleEntity;
import com.fintech.user.entities.UserEntity;
import com.fintech.user.enums.AvailableEnum;
import com.fintech.user.enums.ErrorCodes;
import com.fintech.user.enums.UserRoles;
import com.fintech.user.exceptions.BaseException;
import com.fintech.user.mappers.UserMappers;
import com.fintech.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;


    public UserEntity getUserByPin(String pin) {
        return userRepository.findByPin(pin)
                .orElseThrow(() -> BaseException.of(ErrorCodes.PIN_NOT_FOUND));
    }

    public CommonResponse<String> signUp(UserRequest request) {
        checkUserExist(request.getPin());
        UserEntity user = UserMappers.INSTANCE.requestConvertEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(AvailableEnum.ACTIVE);
        user.setRoles(Collections.singletonList(RoleEntity.builder().role(UserRoles.USER).build()));

        userRepository.save(user);
        return CommonResponse.successInstance(ErrorCodes.SUCCESS.getMessage());
    }

    public CommonResponse<String> signIn(UserRequest request) {
        UserEntity user = getUserByPin(request.getPin());
        checkPassword(user, request);
        return CommonResponse.successInstance(jwtTokenProvider.generateToken(request.getPin()));
    }

    private void checkPassword(UserEntity user, UserRequest request) {
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw BaseException.of(ErrorCodes.PASSWORD_INCORRECT);
    }

    private void checkUserExist(String pin) {
        if (userRepository.findByPin(pin).isPresent())
            throw BaseException.of(ErrorCodes.USER_ALREADY_EXITS);
    }

}
