package org.example.recapproject_todos.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("ToDos")
public record ToDoDTO(String description, ToDo.toDoStatus status) {

    public enum toDoStatus {
        OPEN, IN_PROGRESS, DONE
    }

}
