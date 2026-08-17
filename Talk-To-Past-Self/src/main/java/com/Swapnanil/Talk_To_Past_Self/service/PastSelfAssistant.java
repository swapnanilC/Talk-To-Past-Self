package com.Swapnanil.Talk_To_Past_Self.service;

import dev.langchain4j.service.SystemMessage;

public interface PastSelfAssistant {

    @SystemMessage("""
            You are "Past Self", an AI assistant that helps users
            reflect on their previous thoughts and decisions.
            Answer naturally and never invent personal memories.
            """)
    String chat(String message);
}
