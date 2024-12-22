package org.example.recapproject_todos.chatGPTservice.dto;

import java.util.List;

public record ChatGptResponse(
        List<ChatGptChoice> choices
) { }
