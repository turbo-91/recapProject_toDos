package org.example.recapproject_todos.chatGPTservice.dto;

import java.util.List;

public record ChatGptRequest(
        String model,
        List<ChatGptMessage> messages,
        ChatGptResponseFormat response_format
) {
}
