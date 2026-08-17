package com.Swapnanil.Talk_To_Past_Self.controller;


import com.Swapnanil.Talk_To_Past_Self.dto.ConversationDTO;
import com.Swapnanil.Talk_To_Past_Self.entity.Conversation;
import com.Swapnanil.Talk_To_Past_Self.service.ChatService;
import com.Swapnanil.Talk_To_Past_Self.service.ConversationService;
import com.Swapnanil.Talk_To_Past_Self.service.PastSelfAssistant;
import dev.langchain4j.model.embedding.EmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final PastSelfAssistant assistant;
    private final ChatService chatService;
    private final ConversationService conversationService;
    private final EmbeddingModel embeddingModel;


    @GetMapping
    public String chat(@RequestParam Long userId,@RequestParam String message) {
        return chatService.chat(userId,message);
    }

    @GetMapping("/history")
    public List<ConversationDTO> getHistory(@RequestParam Long userId) {
        return conversationService.getUserConversations(userId);
    }

    @GetMapping("/embedding-test")
    public String embeddingTest() {

        var response = embeddingModel.embed("I am learning Spring Boot");

        return response.content().toString();
    }

    @GetMapping("/similar")
    public List<Conversation> similar(
            @RequestParam Long userId,
            @RequestParam String question) {

        return conversationService.findSimilarConversations(
                userId,
                question
        );
    }
}
