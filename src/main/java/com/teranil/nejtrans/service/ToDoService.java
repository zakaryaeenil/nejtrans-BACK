package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.ToDoRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.mapper.DossierConverter;
import com.teranil.nejtrans.mapper.ToDoConverter;
import com.teranil.nejtrans.mapper.UserConverter;
import com.teranil.nejtrans.model.ToDo;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.ToDoDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class ToDoService {
    private final UserRepository userRepository;
    private final ToDoRepository toDoRepository;
    private final ToDoConverter toDoConverter;
    private final UserConverter userConverter;
    public static List<String> types = Arrays.asList("Important", "Done", "Trash");

    public List<ToDoDTO> getAllTodos() {
        return toDoConverter.entityToDto(toDoRepository.findAll());
    }


    public ResponseEntity<List<ToDoDTO>> getTodos(String type) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        if (Objects.equals(type, "All")) {
            return ResponseEntity.ok().body(toDoConverter.entityToDto(toDoRepository.findByUserId(LoggedInUser.getId())));
        }
        return ResponseEntity.ok().body(toDoConverter.entityToDto(toDoRepository.findByUserIdAndType(LoggedInUser.getId(), type)));
    }

    public ResponseEntity<String> setType(Long id, String type) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        Collection<ToDo> toDos = LoggedInUser.getToDos();
        Optional<ToDo> todo = toDoRepository.findById(id);


        if (todo.isPresent() && toDos.contains(todo.get()) && types.contains(type)) {
            todo.get().setType(type);
            toDoRepository.flush();
            return ResponseEntity.ok().body("Changed type successfully");
        }

        return ResponseEntity.badRequest().body("To do is not found or to do is not yours or type is not correct ! ");


    }

    public ResponseEntity<String> deleteTodo(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        Collection<ToDo> toDos = LoggedInUser.getToDos();
        Optional<ToDo> todo = toDoRepository.findById(id);
        if (todo.isPresent() && toDos.contains(todo.get())) {
            toDoRepository.deleteById(todo.get().getId());
            return ResponseEntity.ok().body("To do deleted successfully ");
        }
        return ResponseEntity.badRequest().body("To do not found or is not yours!");
    }

    public ResponseEntity<String> createTodo(ToDoDTO toDoDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        toDoDTO.setUser(userConverter.entityToDto(LoggedInUser));
        if (types.contains(toDoDTO.getType())) {
            toDoRepository.save(toDoConverter.dtoToEntity(toDoDTO));
            return ResponseEntity.ok().body("saved successfully");

        }
        return ResponseEntity.badRequest().body("please insert correct type");
    }
}
