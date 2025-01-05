package org.example.recapproject_todos.chatGPTservice.controller;

import org.example.recapproject_todos.chatGPTservice.service.ChatGptApiService;
import org.example.recapproject_todos.model.ToDo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat-gpt")
public class ChatGptController {

    private final ChatGptApiService chatGptApiService;

    public ChatGptController(ChatGptApiService chatGptApiService) {
        this.chatGptApiService = chatGptApiService;
    }

    @PostMapping("/check-spelling")
    public String checkToDoForSpelling(@RequestBody ToDo toDo) {
        return chatGptApiService.checkToDoForSpelling(toDo);
    }
}
