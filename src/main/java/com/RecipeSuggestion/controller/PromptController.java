package com.RecipeSuggestion.controller;

import com.RecipeSuggestion.client.RecipeSuggestionAPIService;
import com.RecipeSuggestion.dto.*;
import com.RecipeSuggestion.service.PromptLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/prompt")
public class PromptController {
    private final RecipeSuggestionAPIService recipeSuggestionAPIService;
    private final PromptLogService promptLogService;
    private static final Logger logger = LoggerFactory.getLogger(PromptController.class);

    @GetMapping("/logs/{userName}")
    public  ResponseEntity<List<PromptLogResponseDto>> getLogs(@PathVariable String userName) {
        try {
            List<PromptLogResponseDto> promptLogResponseDtoList = promptLogService.getLogs(userName);
            return new ResponseEntity<>(promptLogResponseDtoList, HttpStatus.OK);

        } catch (RuntimeException ex) {

            logger.error((ex.getMessage()));
            List<PromptLogResponseDto> promptLogResponseDtoList = new ArrayList<PromptLogResponseDto>();
            return  new ResponseEntity<>(promptLogResponseDtoList, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping("/recommend")
    public ResponseEntity<PromptLogResponseDto> recommend(@RequestBody PromptLogRequestDto request) {
        try {
            RecipeSuggestionRequestDto recipeSuggestionRequestDto = new RecipeSuggestionRequestDto();
            recipeSuggestionRequestDto.setInput(request.getInput());


            RecipeSuggestionResponseDto recipeSuggestionResponseDto =  recipeSuggestionAPIService.recommendRecipe(recipeSuggestionRequestDto);
            recipeSuggestionResponseDto.setUsername(request.getUsername());

            PromptLogResponseDto promptLogResponseDto =  promptLogService.savePrompt(recipeSuggestionResponseDto);

            return new ResponseEntity<>(promptLogResponseDto, HttpStatus.OK);

        } catch (RestClientException ex) {
            logger.error(ex.getMessage());
            PromptLogResponseDto promptLogResponseDto = new PromptLogResponseDto();
            promptLogResponseDto.setMessage(ex.getMessage());
            return new ResponseEntity<>(promptLogResponseDto, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            logger.error(ex.getMessage());
            PromptLogResponseDto promptLogResponseDto = new PromptLogResponseDto();
            promptLogResponseDto.setMessage(ex.getMessage());
            return new ResponseEntity<>(promptLogResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
