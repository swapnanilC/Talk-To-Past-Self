package com.Swapnanil.Talk_To_Past_Self.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(columnDefinition = "TEXT")
    private String userQuestion;

    @Column(columnDefinition = "TEXT")
    private String aiAnswer;

    private LocalDateTime createdAt;

    @Column(columnDefinition = "vector(3072)")
    private float[] embedding;
}