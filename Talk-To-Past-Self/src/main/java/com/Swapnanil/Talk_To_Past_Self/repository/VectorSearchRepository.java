package com.Swapnanil.Talk_To_Past_Self.repository;

import com.Swapnanil.Talk_To_Past_Self.entity.Conversation;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class VectorSearchRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Conversation> findSimilarConversations(
            Long userId,
            String embedding) {

        String sql = """
        SELECT id, user_id, user_question, ai_answer, created_at
        FROM conversations
        WHERE user_id = ?
          AND (embedding <=> ?::vector) < 0.5
        ORDER BY embedding <=> ?::vector
        LIMIT 5
        """;


        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Conversation conversation = new Conversation();

                    conversation.setId(rs.getLong("id"));
                    conversation.setUserId(rs.getLong("user_id"));
                    conversation.setUserQuestion(
                            rs.getString("user_question"));
                    conversation.setAiAnswer(
                            rs.getString("ai_answer"));
                    conversation.setCreatedAt(
                            rs.getTimestamp("created_at")
                                    .toLocalDateTime());

                    return conversation;
                },
                userId,
                embedding,
                embedding
        );
    }
}



