package com.Swapnanil.Talk_To_Past_Self.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationDTO {

    private String question;
    private String answer;
    private LocalDateTime createdAt;

}
