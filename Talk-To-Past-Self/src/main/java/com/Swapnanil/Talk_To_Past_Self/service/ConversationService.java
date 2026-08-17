package com.Swapnanil.Talk_To_Past_Self.service;


import com.Swapnanil.Talk_To_Past_Self.dto.ConversationDTO;
import com.Swapnanil.Talk_To_Past_Self.entity.Conversation;
import com.Swapnanil.Talk_To_Past_Self.repository.ConversationRepository;
import com.Swapnanil.Talk_To_Past_Self.repository.VectorSearchRepository;
import dev.langchain4j.model.embedding.EmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final EmbeddingModel embeddingModel;
    private final VectorSearchRepository vectorSearchRepository;

    public void saveConversation(
            Long userId,
            String question,
            String answer) {

        Conversation conversation = new Conversation();

        conversation.setUserId(userId);
        conversation.setUserQuestion(question);
        conversation.setAiAnswer(answer);
        conversation.setCreatedAt(LocalDateTime.now());

        String text = question + " " + answer;

        float[] embedding = embeddingModel
                .embed(text)
                .content()
                .vector();

        conversation.setEmbedding(embedding);

        conversationRepository.save(conversation);

    }

    public List<ConversationDTO> getUserConversations(Long userId) {

        return conversationRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(conversation -> new ConversationDTO(
                        conversation.getUserQuestion(),
                        conversation.getAiAnswer(),
                        conversation.getCreatedAt()
                ))
                .toList();
    }

    public List<Conversation> findSimilarConversations(
            Long userId,
            String question) {

        float[] vector = embeddingModel
                .embed(question)
                .content()
                .vector();

        String embedding = Arrays.toString(vector)
                .replace(" ", "");

        System.out.println(embedding);

        return vectorSearchRepository.findSimilarConversations(
                userId,
                embedding
        );
    }

}
