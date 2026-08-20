package com.Swapnanil.Talk_To_Past_Self.dto;

import dev.langchain4j.model.output.structured.Description;

public record MemoryResponse(

        @Description("""
                Important long-term information about the user.
                Return null if the conversation contains no important
                long-term memory.
                """)
        String memory,

        @Description("""
                Memory type.
                Allowed values: GOAL, PREFERENCE, FACT, DECISION, PLAN, NONE.
                Return NONE if there is no important memory.
                """)
        String type
) {
}