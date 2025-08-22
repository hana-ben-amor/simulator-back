// src/main/java/com/example/simulator/controller/ChatController.java
package com.example.simulator.controller;

import com.example.simulator.dto.ai.ChatRequest;
import com.example.simulator.service.ai.ChatService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chat;

    @PostMapping(value="/chat", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ChatResponse chat(@RequestBody com.example.simulator.dto.ai.ChatRequest req) {
        return new ChatResponse(chat.chat(req));
    }

    public record ChatResponse(String answer) {}
}
