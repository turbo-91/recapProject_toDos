package org.example.recapproject_todos.repo;

import org.example.recapproject_todos.model.ToDo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepo extends MongoRepository<ToDo, String> {
}
