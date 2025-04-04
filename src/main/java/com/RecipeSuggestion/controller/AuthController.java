package com.RecipeSuggestion.controller;

import com.RecipeSuggestion.dto.AuthRequest;
import com.RecipeSuggestion.dto.AuthResponse;
import com.RecipeSuggestion.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {

            AuthResponse response = authService.authenticate(authRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (RuntimeException ex) {
            logger.error(ex.getMessage());
            return new  ResponseEntity<>(new AuthResponse(null, ex.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
        try {
            AuthResponse response = authService.register(authRequest);
            return  new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (RuntimeException ex) {
            logger.error(ex.getMessage());
            return  new ResponseEntity<>(new AuthResponse(null, ex.getMessage()), HttpStatus.BAD_REQUEST );
        }

    }


}
