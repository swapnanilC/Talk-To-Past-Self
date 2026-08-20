package com.Swapnanil.Talk_To_Past_Self.service;

import com.Swapnanil.Talk_To_Past_Self.dto.MemoryResponse;
import com.Swapnanil.Talk_To_Past_Self.dto.MemorySearchResult;
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
            String question
    ) {

        float[] embedding = embeddingModel
                .embed(question)
                .content()
                .vector();

        String vectorString = Arrays.toString(embedding)
                .replace(" ", "");

        List<MemorySearchResult> results =
                memoryVectorSearchRepository.findSimilarMemories(
                        userId,
                        vectorString,
                        0.5
                );

        results.forEach(result ->
                System.out.println(
                        "Memory: " + result.memory().getMemory()
                                + " | Distance: " + result.distance()
                )
        );

        return results.stream()
                .map(MemorySearchResult::memory)
                .toList();
    }

    public boolean hasSimilarMemory(
            Long userId,
            String memoryText
    ) {

        float[] embedding = embeddingModel
                .embed(memoryText)
                .content()
                .vector();

        String vectorString = Arrays.toString(embedding)
                .replace(" ", "");

        List<MemorySearchResult> similarMemories =
                memoryVectorSearchRepository.findSimilarMemories(
                        userId,
                        vectorString,
                        0.2
                );

        return !similarMemories.isEmpty();
    }

    public List<MemoryResponse> getMemories(Long userId) {

        return memoryRepository
                .findMemoriesByUserId(userId)
                .stream()
                .map(row -> new MemoryResponse(
                        (String) row[2],
                        (String) row[3]
                ))
                .toList();
    }

}
