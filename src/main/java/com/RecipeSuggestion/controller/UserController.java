package com.RecipeSuggestion.controller;

import com.RecipeSuggestion.dto.AuthResponse;
import com.RecipeSuggestion.dto.UserDto;
import com.RecipeSuggestion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{userName}")
    public ResponseEntity<UserDto> getUserByUserName(@PathVariable String userName) {
        try {
            UserDto user = userService.getUserByUsername(userName);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UsernameNotFoundException ex) {
            logger.error(ex.getMessage());
            UserDto user = new UserDto();
            user.setMessage(ex.getMessage());
            return new  ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }



    }
}
