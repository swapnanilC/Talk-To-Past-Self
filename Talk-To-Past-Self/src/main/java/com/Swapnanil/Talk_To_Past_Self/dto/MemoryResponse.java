package com.Swapnanil.Talk_To_Past_Self.dto;

import dev.langchain4j.model.output.structured.Description;

public record MemoryResponse(
        @Description("The important long-term memory extracted from the conversation")
        String memory,

        @Description("The type of memory: GOAL, PREFERENCE, FACT, DECISION, or PLAN")
        String type
) {
}
