package com.teranil.nejtrans.mapper;

import com.teranil.nejtrans.model.ToDo;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.ToDoDTO;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ToDoConverter {
    public ToDoDTO entityToDto(ToDo todo) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(todo, ToDoDTO.class);
    }

    public List<ToDoDTO> entityToDto(List<ToDo> todo) {

        return todo.stream().map(this::entityToDto).collect(Collectors.toList());

    }

    public Collection<ToDoDTO> entityToDto(Collection<ToDo> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());

    }

    public ToDo dtoToEntity(ToDoDTO dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, ToDo.class);

    }

    public List<ToDo> dtoToEntity(List<ToDoDTO> dto) {
        return dto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}