package com.RecipeSuggestion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecipeSuggestionResponseDto extends ResponseDto {
    private  String input;
    private String answer;
    private String username;
}
