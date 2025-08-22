package com.example.simulator.service.ai;

import com.example.simulator.dto.ai.AiContext;
import com.example.simulator.dto.ai.ChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final AiProvider ai; // le seul bean actif sera GeminiProvider

    public String ask(String system, String message) {
        var ctx = new AiContext(system, null, null, null);
        return ai.chat(new ChatRequest(message, ctx));
    }

    public String chat(ChatRequest req) {
        return ai.chat(req);
    }
}
