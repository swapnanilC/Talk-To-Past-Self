package com.Swapnanil.Talk_To_Past_Self.service;


import com.Swapnanil.Talk_To_Past_Self.entity.Conversation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final PastSelfAssistant assistant;
    private final ConversationService conversationService;

    public String chat(Long userId, String question) {

        List<Conversation> relevantConversations =
                conversationService.findSimilarConversations(
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


        String prompt = """
            You are "Past Self", an AI assistant that helps users
            reflect on their previous thoughts, decisions and experiences.

            Use the following past conversations as context.

            --- PAST CONVERSATIONS ---
            %s
            --- END PAST CONVERSATIONS ---

            Current question:
            %s

            Answer naturally based on the past conversations.
            If the answer cannot be found in the provided context,
            clearly say that you don't have enough information.
            Never invent personal memories.
            """.formatted(context, question);

        String answer = assistant.chat(prompt);



        conversationService.saveConversation(
                userId,
                question,
                answer
        );

        return answer;
    }
}
