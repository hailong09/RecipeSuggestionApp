package com.RecipeSuggestion.repository;

import com.RecipeSuggestion.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




@ExtendWith(MockitoExtension.class)
public class UserRepositoryUnitTest {
    @Mock
    private UserRepository userRepository;

    @Test
    public void testGetUserByUsername() {
        // Given
        User user = new User();
        user.setUsername("test");
        user.setId(1L);
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        //When
        Optional<User> foundUser = userRepository.findByUsername("test");

        // then
        assertTrue(foundUser.isPresent());
        assertEquals("test", foundUser.get().getUsername());

    }
}
