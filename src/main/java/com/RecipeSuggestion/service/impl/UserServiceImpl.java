package com.RecipeSuggestion.service.impl;

import com.RecipeSuggestion.dto.UserDto;
import com.RecipeSuggestion.entity.User;
import com.RecipeSuggestion.repository.UserRepository;
import com.RecipeSuggestion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            UserDto userDto = new UserDto();
            userDto.setUsername(user.get().getUsername());
            userDto.setId(user.get().getId());

            return userDto;
        }

        throw new UsernameNotFoundException("User not found.");

    }
}
