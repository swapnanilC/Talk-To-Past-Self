package com.Swapnanil.Talk_To_Past_Self.repository;

import com.Swapnanil.Talk_To_Past_Self.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query(value = """
        SELECT *
        FROM conversations
        WHERE user_id = :userId
        ORDER BY embedding <=> CAST(:embedding AS vector)
        LIMIT 5
        """, nativeQuery = true)
    List<Conversation> findSimilarConversations(
            @Param("userId") Long userId,
            @Param("embedding") String embedding);
}