package com.RecipeSuggestion.service;

import com.RecipeSuggestion.dto.AuthRequest;
import com.RecipeSuggestion.dto.AuthResponse;

public interface AuthService {
    public AuthResponse register(AuthRequest authRequest);
    public AuthResponse authenticate(AuthRequest authRequest);
}
