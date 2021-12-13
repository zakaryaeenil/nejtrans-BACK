package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.dto.ToDoDTO;
import com.teranil.nejtrans.service.ToDoService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/todos")
public class ToDoController {
private final ToDoService toDoService;

    @ApiOperation(value = "Used by Logged in client to get his to do list by type")
    @GetMapping("/{type}")
    public ResponseEntity<List<ToDoDTO>> getUserTodos(@PathVariable String type) {
        return toDoService.getTodos(type);
    }

    @ApiOperation(value = "Used by Logged in client to delete his to do ")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        return toDoService.deleteTodo(id);
    }

    @ApiOperation(value = "Used by Logged in client to change his to do list type ")
    @PutMapping("/{id}/{type}")
    public ResponseEntity<String> setTodoType(@PathVariable Long id,@PathVariable String type) {
        return toDoService.setType(id,type);
    }

    @PostMapping("/create")
    public ResponseEntity<String> CreateTodo(@RequestBody ToDoDTO toDoDTO){
       return toDoService.createTodo(toDoDTO);
    }

}

