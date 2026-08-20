package com.Swapnanil.Talk_To_Past_Self.repository;

import com.Swapnanil.Talk_To_Past_Self.dto.MemorySearchResult;
import com.Swapnanil.Talk_To_Past_Self.entity.Memory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemoryVectorSearchRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<MemorySearchResult> findSimilarMemories(
            Long userId,
            String embedding,
            double threshold) {

        String sql = """
                SELECT id, user_id, memory, type, created_at,
                       (embedding <=> ?::vector) AS distance
                FROM memories
                WHERE user_id = ?
                  AND (embedding <=> ?::vector) < ?
                ORDER BY embedding <=> ?::vector
                LIMIT 5
            """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Memory memory = new Memory();

                    memory.setId(rs.getLong("id"));
                    memory.setUserId(rs.getLong("user_id"));
                    memory.setMemory(rs.getString("memory"));
                    memory.setType(rs.getString("type"));
                    memory.setCreatedAt(
                            rs.getTimestamp("created_at")
                                    .toLocalDateTime()
                    );

                    double distance = rs.getDouble("distance");

                    return new MemorySearchResult(
                            memory,
                            distance);
                },
                embedding,
                userId,
                embedding,
                threshold,
                embedding
        );
    }
}
