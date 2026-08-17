package com.Swapnanil.Talk_To_Past_Self.config;


import com.Swapnanil.Talk_To_Past_Self.service.PastSelfAssistant;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiEmbeddingModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Bean
    public ChatModel chatModel(
            @Value("${gemini.api.key}") String apiKey) {

        return GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-3.6-flash")
                .build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(10);
    }

    @Bean
    public PastSelfAssistant pastSelfAssistant(
            ChatModel chatModel,
            ChatMemory chatMemory) {

        return AiServices.builder(PastSelfAssistant.class)
                .chatModel(chatModel)
                .chatMemory(chatMemory)
                .build();
    }


    @Bean
    public EmbeddingModel embeddingModel(
            @Value("${gemini.api.key}") String apiKey) {

        return GoogleAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-embedding-001")
                .build();
    }
}
