package com.Swapnanil.Talk_To_Past_Self.service;


import com.Swapnanil.Talk_To_Past_Self.dto.MemoryResponse;
import com.Swapnanil.Talk_To_Past_Self.entity.Conversation;
import com.Swapnanil.Talk_To_Past_Self.entity.Memory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final PastSelfAssistant assistant;
    private final ConversationService conversationService;
    private final MemoryExtractionService memoryExtractionService;
    private final MemoryService memoryService;

    public String chat(Long userId, String question) {

        List<Conversation> relevantConversations =
                conversationService.findSimilarConversations(
                        userId,
                        question
                );

        List<Memory> relevantMemories =
                memoryService.findSimilarMemories(
                        userId,
                        question
                );

        String context = relevantConversations.stream()
                .map(c -> """
                User: %s
                AI: %s
                Date: %s
                """.formatted(
                        c.getUserQuestion(),
                        c.getAiAnswer(),
                        c.getCreatedAt()
                ))
                .collect(Collectors.joining("\n"));


        String memoryContext = relevantMemories.stream()
                .map(m -> """
                Memory: %s
                Type: %s
                Date: %s
                """.formatted(
                        m.getMemory(),
                        m.getType(),
                        m.getCreatedAt()
                ))
                .collect(Collectors.joining("\n"));


        String prompt = """
            You are "Past Self", an AI assistant that helps users
            reflect on their previous thoughts, decisions, goals,
            preferences, and experiences.

            You have access to two sources of information:

            1. Past Conversations
            These contain previous conversations between the user and AI.

            2. Long-Term Memories
            These contain important information extracted from previous
            conversations, such as the user's goals, preferences, facts,
            decisions, and plans.

            Use these sources as context when answering the user's question.

            --- PAST CONVERSATIONS ---
            %s
            --- END PAST CONVERSATIONS ---

            --- LONG-TERM MEMORIES ---
            %s
            --- END LONG-TERM MEMORIES ---

            Current question:
            %s

            Instructions:
            - Answer naturally and conversationally.
            - Use the provided conversations and memories when relevant.
            - Prioritize information that is directly relevant to the question.
            - Do not invent personal memories or facts about the user.
            - If the information cannot be found in the provided context,
              clearly say that you don't have enough information.
            - Do not mention the internal retrieval process or embeddings.
            """.formatted(
                    context,
                    memoryContext,
                    question
            );

        String answer = assistant.chat(prompt);

        MemoryResponse memory = memoryExtractionService.extractMemory(
                "User: " + question + "\nAI: " + answer
        );

        // Save memory if one was found
        if (memory != null
                && memory.memory() != null
                && !memory.memory().isBlank()) {

            memoryService.saveMemory(
                    userId,
                    memory.memory(),
                    memory.type()
            );
        }

        conversationService.saveConversation(
                userId,
                question,
                answer
        );

        return answer;
    }
}
