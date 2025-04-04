package com.RecipeSuggestion.service;


import com.RecipeSuggestion.dto.PromptLogResponseDto;
import com.RecipeSuggestion.dto.RecipeSuggestionResponseDto;

import java.util.List;


public interface PromptLogService {
    public List<PromptLogResponseDto> getLogs(String userName);
    public PromptLogResponseDto savePrompt(RecipeSuggestionResponseDto recipeSuggestionResponseDto);
}
