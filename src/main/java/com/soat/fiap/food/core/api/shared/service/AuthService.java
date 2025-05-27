package com.soat.fiap.food.core.api.shared.service;

import com.soat.fiap.food.core.api.user.domain.ports.out.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("authService")
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean canAccessUser(Authentication authentication, Long userId) {
        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .map(user -> user.getId().equals(userId) && !user.isGuest())
                .orElse(false);
    }
}
