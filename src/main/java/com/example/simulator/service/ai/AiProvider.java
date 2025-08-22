package com.example.simulator.service.ai;
import com.example.simulator.dto.ai.ChatRequest;
public interface AiProvider {
    String chat(ChatRequest req);
}
