package org.example.recapproject_todos.controller;

import lombok.RequiredArgsConstructor;
import org.example.recapproject_todos.exception.IdNotFoundException;
import org.example.recapproject_todos.model.ToDo;
import org.example.recapproject_todos.model.ToDoDTO;
import org.example.recapproject_todos.service.ToDoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/todo")
    public List<ToDo> getAll() {
        return toDoService.getAllToDos();
    }

    @GetMapping("/todo/{id}")
    public ToDo getToDoById(@PathVariable String id) throws IdNotFoundException {
        return toDoService.getToDoById(id);
    }

    @PostMapping
    public ToDo createTodo(@RequestBody ToDoDTO newToDo ){
        return toDoService.createToDo(newToDo);
    }

    @PutMapping("/todo/{id}")
    public ToDo updateToDo(@RequestBody ToDo updatedTodo) throws IdNotFoundException {
        return toDoService.updateTodo(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ToDo deleteTodo(@PathVariable String id) throws IdNotFoundException {
        return toDoService.deleteTodo(id);
    }
}
