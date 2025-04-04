package com.RecipeSuggestion.client;

import com.RecipeSuggestion.dto.RecipeSuggestionRequestDto;
import com.RecipeSuggestion.dto.RecipeSuggestionResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Service
public class RecipeSuggestionAPIService {
    private final RestTemplate restTemplate;

    @Value("${app.recipe.baseUrl}")
    private String recipeBaseUrl;

    public RecipeSuggestionAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    public RecipeSuggestionResponseDto recommendRecipe(RecipeSuggestionRequestDto query) {
          try {
              ResponseEntity<RecipeSuggestionResponseDto> response = restTemplate.postForEntity(recipeBaseUrl + "/recommend", query, RecipeSuggestionResponseDto.class);
              return response.getBody();

          } catch (RestClientException ex) {
              throw ex;
          }

    }
}
