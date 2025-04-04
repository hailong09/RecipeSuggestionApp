package com.RecipeSuggestion.service;

import com.RecipeSuggestion.dto.UserDto;

public interface UserService {
    public UserDto getUserByUsername(String username);

}
