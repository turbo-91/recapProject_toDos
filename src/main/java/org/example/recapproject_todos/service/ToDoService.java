package org.example.recapproject_todos.service;

import lombok.AllArgsConstructor;
import org.example.recapproject_todos.exception.IdNotFoundException;
import org.example.recapproject_todos.model.ToDo;
import org.example.recapproject_todos.model.ToDoDTO;
import org.example.recapproject_todos.repo.ToDoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {
    private final ToDoRepo toDoRepo;
    private final IdService idService;

    public ToDoService(ToDoRepo toDoRepo, IdService idService) {
        this.toDoRepo = toDoRepo;
        this.idService = idService;
    }

    public List<ToDo> getAllToDos() {
        return toDoRepo.findAll();
    }

    public ToDo getToDoById(String id) throws IdNotFoundException {
        return toDoRepo.findById(id)
                .orElseThrow(() -> new IdNotFoundException("No todo found with ID: " + id));
    }

    public ToDo createToDo(ToDoDTO newToDo) {
        ToDo todo = new ToDo(
                idService.generateId(),
                newToDo.description(),
                newToDo.status()
        );
        return toDoRepo.save(todo);
    }

    public ToDo updateTodo(ToDo updatedToDo) throws IdNotFoundException {
        if (toDoRepo.existsById(updatedToDo.id())){
            return toDoRepo.save(updatedToDo);
        }else {
            throw new IdNotFoundException("No todo found with ID: " + updatedToDo.id());
        }
    }

    public ToDo deleteTodo(String id) throws IdNotFoundException {
        if (toDoRepo.existsById(id)){
            ToDo deletedTodo = toDoRepo.findById(id).orElseThrow();
            toDoRepo.deleteById(id);
            return deletedTodo;
        }else {
            throw new IdNotFoundException("No todo found with ID: " + id);
        }
    }

}
