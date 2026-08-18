package com.Swapnanil.Talk_To_Past_Self.repository;

import com.Swapnanil.Talk_To_Past_Self.entity.Memory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemoryVectorSearchRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Memory> findSimilarMemories(
            Long userId,
            String embedding) {

        String sql = """
                SELECT id, user_id, memory, type, created_at
                FROM memories
                WHERE user_id = ?
                  AND (embedding <=> ?::vector) < 0.5
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

                    return memory;
                },
                userId,
                embedding,
                embedding
        );
    }
}
