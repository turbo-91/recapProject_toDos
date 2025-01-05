package org.example.recapproject_todos.chatGPTservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class ChatGptControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void checkToDoForSpelling_ShouldReturnCorrectedText_whenValidToDoIsProvided() throws Exception {
        // GIVEN
        server.expect(requestTo("https://api.openai.com/v1/chat/completions"))
                .andExpect(method(org.springframework.http.HttpMethod.POST))
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
        mockMvc.perform(post("/api/chat-gpt/check-spelling")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": "1",
                                    "description": "I have to finish te bootcmp.",
                                    "status": "OPEN"
                                }
                                """))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().string("I have to finish the bootcamp."));
    }

    @Test
    void checkToDoForSpelling_ShouldHandleEmptyDescription() throws Exception {
        // GIVEN
        server.expect(requestTo("https://api.openai.com/v1/chat/completions"))
                .andExpect(method(org.springframework.http.HttpMethod.POST))
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
        mockMvc.perform(post("/api/chat-gpt/check-spelling")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": "2",
                                    "description": "",
                                    "status": "OPEN"
                                }
                                """))
                // THEN
                .andExpect(status().isOk())
                .andExpect(content().string("The description is empty."));
    }

    @Test
    void checkToDoForSpelling_ShouldReturnError_whenAPIKeyIsMissing() throws Exception {
        // GIVEN
        server.expect(requestTo("https://api.openai.com/v1/chat/completions"))
                .andExpect(method(org.springframework.http.HttpMethod.POST))
                .andRespond(withSuccess("""
                        {
                          "error": {
                            "message": "Invalid API key provided.",
                            "type": "authentication_error"
                          }
                        }
                        """, MediaType.APPLICATION_JSON));

        // WHEN
        mockMvc.perform(post("/api/chat-gpt/check-spelling")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": "3",
                                    "description": "This should fail.",
                                    "status": "OPEN"
                                }
                                """))
                // THEN
                .andExpect(status().isOk()) // Adjust as needed for error responses
                .andExpect(content().string("Invalid API key provided."));
    }
}