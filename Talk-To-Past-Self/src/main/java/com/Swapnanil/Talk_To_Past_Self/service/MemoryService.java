package com.Swapnanil.Talk_To_Past_Self.service;

import com.Swapnanil.Talk_To_Past_Self.entity.Memory;
import com.Swapnanil.Talk_To_Past_Self.repository.MemoryRepository;
import com.Swapnanil.Talk_To_Past_Self.repository.MemoryVectorSearchRepository;
import dev.langchain4j.model.embedding.EmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoryService {

    private final MemoryRepository memoryRepository;
    private final EmbeddingModel embeddingModel;
    private final MemoryVectorSearchRepository memoryVectorSearchRepository;

    public void saveMemory(
            Long userId,
            String memoryText,
            String type) {

        Memory memory = new Memory();

        memory.setUserId(userId);
        memory.setMemory(memoryText);
        memory.setType(type);
        memory.setCreatedAt(LocalDateTime.now());

        float[] embedding = embeddingModel
                .embed(memoryText)
                .content()
                .vector();

        memory.setEmbedding(embedding);

        memoryRepository.save(memory);
    }

    public List<Memory> findSimilarMemories(
            Long userId,
            String question) {

        float[] embedding = embeddingModel
                .embed(question)
                .content()
                .vector();

        String vectorString = Arrays.toString(embedding)
                .replace(" ", "");

        return memoryVectorSearchRepository.findSimilarMemories(
                userId,
                vectorString
        );
    }
}
