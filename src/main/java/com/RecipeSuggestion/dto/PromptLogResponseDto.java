package com.RecipeSuggestion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromptLogResponseDto extends ResponseDto {
    private Long id;
    private  String input;
    private String answer;
    private Long userId;
}
