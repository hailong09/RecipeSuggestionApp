package com.RecipeSuggestion.repository;

import com.RecipeSuggestion.entity.PromptLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromptLogRepository extends JpaRepository<PromptLog, Long> {
    Optional<List<PromptLog>> findByUserId(Long userId);

}
