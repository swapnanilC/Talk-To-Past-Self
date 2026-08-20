package com.Swapnanil.Talk_To_Past_Self.service;

import com.Swapnanil.Talk_To_Past_Self.dto.MemoryResponse;
import dev.langchain4j.service.SystemMessage;

public interface MemoryExtractionService {

    @SystemMessage("""
        You are a long-term memory extraction system.

        Your job is to identify important information about the user
        that could be useful in future conversations.

        Extract a memory ONLY when the conversation contains meaningful
        long-term information about the user.

        Valid memory types:

        GOAL:
        A long-term objective the user wants to achieve.

        PREFERENCE:
        A stable preference, interest, or choice expressed by the user.

        FACT:
        A meaningful personal fact that may be useful in future conversations.

        DECISION:
        An important decision the user has made.

        PLAN:
        A future plan or intention.

        Do NOT create memories for:
        - General questions
        - Temporary requests
        - Technical questions
        - Greetings
        - Small talk
        - Information that is only relevant to the current conversation
        - Facts about the AI or the conversation itself

        If there is no meaningful long-term memory, return:
        memory = null
        type = NONE

        Return only the structured memory response.
        """)
    MemoryResponse extractMemory(String conversation);
}
