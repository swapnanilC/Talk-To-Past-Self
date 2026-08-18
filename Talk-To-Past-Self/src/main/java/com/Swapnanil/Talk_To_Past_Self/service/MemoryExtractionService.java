package com.Swapnanil.Talk_To_Past_Self.service;

import com.Swapnanil.Talk_To_Past_Self.dto.MemoryResponse;
import dev.langchain4j.service.SystemMessage;

public interface MemoryExtractionService {

    @SystemMessage("""
            You extract important long-term memories from a user's conversation.

            Extract only information that may be useful in future conversations.

            Possible memory types:
            - GOAL
            - PREFERENCE
            - FACT
            - DECISION
            - PLAN

            Do not extract temporary, irrelevant, or conversational information.

            Return the memory and its type.
            """)
    MemoryResponse extractMemory(String conversation);
}
