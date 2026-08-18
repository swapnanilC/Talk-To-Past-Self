package com.Swapnanil.Talk_To_Past_Self.repository;

import com.Swapnanil.Talk_To_Past_Self.entity.Memory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryRepository extends JpaRepository<Memory, Long> {
}