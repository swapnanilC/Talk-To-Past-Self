package com.Swapnanil.Talk_To_Past_Self.repository;

import com.Swapnanil.Talk_To_Past_Self.entity.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemoryRepository extends JpaRepository<Memory, Long> {

    @Query("""
        SELECT m.id, m.userId, m.memory, m.type, m.createdAt
        FROM Memory m
        WHERE m.userId = :userId
        ORDER BY m.createdAt DESC
        """)
    List<Object[]> findMemoriesByUserId(@Param("userId") Long userId);


}