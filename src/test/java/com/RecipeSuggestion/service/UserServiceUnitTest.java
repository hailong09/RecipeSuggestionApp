package com.RecipeSuggestion.service;

import com.RecipeSuggestion.dto.UserDto;
import com.RecipeSuggestion.entity.User;
import com.RecipeSuggestion.repository.UserRepository;
import com.RecipeSuggestion.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testGetUserByUsername() {
        User user = new User();
        user.setUsername("test");

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        UserDto foundUser = userService.getUserByUsername("test");
        assertNotNull(foundUser);

        assertEquals("test", foundUser.getUsername());

    }
}
