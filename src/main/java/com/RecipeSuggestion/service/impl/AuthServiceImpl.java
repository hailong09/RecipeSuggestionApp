package com.RecipeSuggestion.service.impl;

import com.RecipeSuggestion.dto.AuthRequest;
import com.RecipeSuggestion.dto.AuthResponse;
import com.RecipeSuggestion.entity.User;
import com.RecipeSuggestion.repository.UserRepository;
import com.RecipeSuggestion.service.AuthService;
import com.RecipeSuggestion.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;



    @Override
    public AuthResponse register(AuthRequest authRequest) {
        User newUser = new User();
        newUser.setUsername(authRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        userRepository.save(newUser);

        String token =  jwtUtil.generateToken(newUser.getUsername());
        if(token.isEmpty()) {
           throw  new RuntimeException("Internal server error.");
        }

        return  new AuthResponse(token, null);
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {


        Optional<User> user = userRepository.findByUsername(authRequest.getUsername());
        if (user.isPresent() && passwordEncoder.matches(authRequest.getPassword(), user.get().getPassword())) {
            String token = jwtUtil.generateToken(user.get().getUsername());

            return new AuthResponse(token, null);
        }

        throw  new RuntimeException("Invalid authentication.");

    }
}
