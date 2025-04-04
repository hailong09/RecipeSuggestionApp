package com.RecipeSuggestion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "prompt_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromptLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 3000)
    private  String answer;

    @Column(length = 1000)
    private  String input;

    @ManyToOne()
    @JoinColumn(name = "userId")
    private  User user;
}
