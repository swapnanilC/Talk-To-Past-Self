package com.Swapnanil.Talk_To_Past_Self.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "memories")
@Getter
@Setter
@NoArgsConstructor
public class Memory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(columnDefinition = "TEXT")
    private String memory;

    private String type;

    private LocalDateTime createdAt;

    @Column(columnDefinition = "vector(3072)")
    private float[] embedding;
}
