package com.fintech.user.config;

import com.fintech.user.entities.UserEntity;
import com.fintech.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtUserDetails implements UserDetailsService {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        String pin = jwtTokenProvider.getPinFromJWT(token);
        UserEntity user = userService.getUserByPin(pin);
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(userRole ->
                authorities.add(new SimpleGrantedAuthority(userRole.getRole().toString())));
        return new User(pin, user.getPassword(), authorities);
    }
}
