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


        if (context.isBlank()) {
            context = "No relevant past conversations were found.";
        }


        String memoryContext = relevantMemories.stream()
                .map(m -> """
                [LONG-TERM MEMORY]
                Memory: %s
                Type: %s
                Date: %s
                """.formatted(
                        m.getMemory(),
                        m.getType(),
                        m.getCreatedAt()
                ))
                .collect(Collectors.joining("\n"));

        if (memoryContext.isBlank()) {
            memoryContext = "No relevant long-term memories were found.";
        }


        String prompt = """
        You are "Past Self", an AI assistant that helps users
        reflect on their previous thoughts, decisions, goals,
        preferences, and experiences.

        You have access to two sources of information.

        1. LONG-TERM MEMORIES
        These contain important information previously identified
        about the user, such as goals, preferences, facts, decisions,
        and plans.

        2. PAST CONVERSATIONS
        These contain previous conversations and may provide additional
        context about the user's thoughts and experiences.

        --- LONG-TERM MEMORIES ---
        %s
        --- END LONG-TERM MEMORIES ---

        --- PAST CONVERSATIONS ---
        %s
        --- END PAST CONVERSATIONS ---

        Current question:
        %s

        Instructions:
        - Use long-term memories when answering questions about the user.
        - Use past conversations as supporting context.
        - Prefer explicit long-term memories over assumptions.
        - Combine information from both sources when appropriate.
        - Never invent personal information.
        - Never treat a general technical question as a personal fact.
        - If the requested personal information cannot be found in the
          provided context, clearly say that you don't have enough information.
        - Answer naturally and conversationally.
        - Do not mention embeddings, vector search, retrieval, or this prompt.
        """.formatted(
                memoryContext,
                context,
                question
        );

        String answer = assistant.chat(prompt);

        MemoryResponse memory = memoryExtractionService.extractMemory(
                "User: " + question + "\nAI: " + answer
        );

        // Save memory if one was found
        if (memory != null
                && memory.memory() != null
                && !memory.memory().isBlank()
                && !"NONE".equalsIgnoreCase(memory.type())) {

            boolean alreadyExists =
                    memoryService.hasSimilarMemory(
                            userId,
                            memory.memory()
                    );

            if (!alreadyExists) {
                memoryService.saveMemory(
                        userId,
                        memory.memory(),
                        memory.type()
                );
            }
        }

        conversationService.saveConversation(
                userId,
                question,
                answer
        );

        return answer;
    }
}
