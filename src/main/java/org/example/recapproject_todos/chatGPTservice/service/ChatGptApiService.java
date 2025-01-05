package org.example.recapproject_todos.chatGPTservice.service;

import org.example.recapproject_todos.chatGPTservice.dto.ChatGptMessage;
import org.example.recapproject_todos.chatGPTservice.dto.ChatGptRequest;
import org.example.recapproject_todos.chatGPTservice.dto.ChatGptResponse;
import org.example.recapproject_todos.chatGPTservice.dto.ChatGptResponseFormat;
import org.example.recapproject_todos.model.ToDo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ChatGptApiService {

    private final RestClient restClient;

    @Value("${app.openai-api-key}")
    private String apiKey;

    public ChatGptApiService(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    public String checkToDoForSpelling(ToDo toDo) {
        // Create the prompt to check for spelling mistakes in the ToDo description
        String prompt = String.format("Check the following text for spelling mistakes: \"%s\"", toDo.description());

        // Prepare the ChatGPT API request
        ChatGptResponse response = restClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ChatGptRequest(
                        "gpt-4o-mini",
                        List.of(new ChatGptMessage("user", prompt)),
                        new ChatGptResponseFormat("json_object")
                ))
                .retrieve()
                .body(ChatGptResponse.class);

        // Extract and return the response content
        return response.choices().get(0).message().content();
    }
}
