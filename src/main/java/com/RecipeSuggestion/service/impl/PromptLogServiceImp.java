package com.RecipeSuggestion.service.impl;


import com.RecipeSuggestion.dto.PromptLogResponseDto;
import com.RecipeSuggestion.dto.RecipeSuggestionResponseDto;
import com.RecipeSuggestion.entity.PromptLog;
import com.RecipeSuggestion.entity.User;
import com.RecipeSuggestion.repository.PromptLogRepository;
import com.RecipeSuggestion.repository.UserRepository;
import com.RecipeSuggestion.service.PromptLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromptLogServiceImp implements PromptLogService {
    private final PromptLogRepository promptLogRepository;
    private final UserRepository userRepository;


    @Override
    public List<PromptLogResponseDto> getLogs(String userName) {
        // Get user info
        Optional<User> foundUser = userRepository.findByUsername(userName);
        if(foundUser.isEmpty()) {
            throw  new RuntimeException("internal server error.");
        }

        // getListLog
        Optional<List<PromptLog>> promptLogList = promptLogRepository.findByUserId(foundUser.get().getId());

        return promptLogList.get().stream().map(promptLog -> {
            PromptLogResponseDto promptLogResponseDto = new PromptLogResponseDto();
            promptLogResponseDto.setId(promptLog.getId());
            promptLogResponseDto.setInput(promptLog.getInput());
            promptLogResponseDto.setAnswer(promptLog.getAnswer());
            promptLogResponseDto.setUserId(promptLog.getUser().getId());
            return  promptLogResponseDto;

        }).collect(Collectors.toList());
    }

    @Override
    public PromptLogResponseDto savePrompt(RecipeSuggestionResponseDto recipeSuggestionResponseDto) {
        // get User
        Optional<User> foundUser = userRepository.findByUsername(recipeSuggestionResponseDto.getUsername());
        if(foundUser.isEmpty()) {
            throw new  RuntimeException("Internal server error.");
        }

        User user = foundUser.get();

        PromptLog promptLog = new PromptLog();
        promptLog.setInput(recipeSuggestionResponseDto.getInput());
        promptLog.setAnswer(recipeSuggestionResponseDto.getAnswer());
        promptLog.setUser(user);

        PromptLog data =  promptLogRepository.save(promptLog);

        PromptLogResponseDto promptLogResponseDto = new PromptLogResponseDto();
        promptLogResponseDto.setAnswer(promptLog.getAnswer());
        promptLogResponseDto.setUserId(user.getId());
        promptLogResponseDto.setInput(promptLog.getInput());
        promptLogResponseDto.setId(data.getId());

        return promptLogResponseDto;
    }


}
