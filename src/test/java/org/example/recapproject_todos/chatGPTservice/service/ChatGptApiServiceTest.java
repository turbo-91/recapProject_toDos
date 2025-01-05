package org.example.recapproject_todos.chatGPTservice.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.recapproject_todos.chatGPTservice.dto.*;
import org.example.recapproject_todos.model.ToDo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@AutoConfigureMockRestServiceServer
class ChatGptApiServiceIntegrationTest {

    @Autowired
    private ChatGptApiService chatGptApiService;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void checkToDoForSpelling_ShouldReturnCorrectedText_whenValidToDoIsProvided() {
        // GIVEN
        ToDo toDo = new ToDo("1", "I have to finish te bootcmp.", ToDo.toDoStatus.OPEN);
        String prompt = "Check the following text for spelling mistakes: \"I have to finish te bootcmp.\"";

        server.expect(requestTo("https://api.openai.com/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("""
                        {
                          "choices": [
                            {
                              "message": {
                                "role": "assistant",
                                "content": "I have to finish the bootcamp."
                              }
                            }
                          ]
                        }
                        """, MediaType.APPLICATION_JSON));

        // WHEN
        String result = chatGptApiService.checkToDoForSpelling(toDo);

        // THEN
        assertEquals("I have to finish the bootcamp.", result);
        server.verify(); // Verify that the mocked server received the correct call
    }

    @Test
    void checkToDoForSpelling_ShouldHandleEmptyDescription() {
        // GIVEN
        ToDo toDo = new ToDo("2", "", ToDo.toDoStatus.OPEN);
        String prompt = "Check the following text for spelling mistakes: \"\"";

        server.expect(requestTo("https://api.openai.com/v1/chat/completions"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("""
                        {
                          "choices": [
                            {
                              "message": {
                                "role": "assistant",
                                "content": "The description is empty."
                              }
                            }
                          ]
                        }
                        """, MediaType.APPLICATION_JSON));

        // WHEN
        String result = chatGptApiService.checkToDoForSpelling(toDo);

        // THEN
        assertEquals("The description is empty.", result);
        server.verify();
    }
}
