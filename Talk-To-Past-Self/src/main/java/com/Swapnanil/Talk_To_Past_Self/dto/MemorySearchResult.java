package com.Swapnanil.Talk_To_Past_Self.dto;

import com.Swapnanil.Talk_To_Past_Self.entity.Memory;

public record MemorySearchResult(
        Memory memory,
        double distance
) {
}
