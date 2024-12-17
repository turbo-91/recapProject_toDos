package org.example.recapproject_todos.model;

public record ToDo(String id, String description, toDoStatus status) {

    public enum toDoStatus {
        OPEN, IN_PROGRESS, DONE
    }

}
