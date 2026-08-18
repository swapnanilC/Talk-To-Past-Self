package com.Swapnanil.Talk_To_Past_Self.config;


import com.Swapnanil.Talk_To_Past_Self.service.MemoryExtractionService;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIServiceConfig {

    @Bean
    public MemoryExtractionService memoryExtractionService(
            ChatModel chatModel) {

        return AiServices.builder(MemoryExtractionService.class)
                .chatModel(chatModel)
                .build();
    }

}
