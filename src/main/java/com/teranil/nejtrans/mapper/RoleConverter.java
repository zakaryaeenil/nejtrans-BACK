package com.teranil.nejtrans.mapper;

import com.teranil.nejtrans.model.Role;
import com.teranil.nejtrans.model.dto.RoleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleConverter {

    public RoleDTO entityToDto(Role role) {

        ModelMapper mapper = new ModelMapper();

        return mapper.map(role, RoleDTO.class);

    }

    public List<RoleDTO> entityToDto(List<Role> roles) {

        return roles.stream().map(this::entityToDto).collect(Collectors.toList());

    }


    public Role dtoToEntity(RoleDTO dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Role.class);
    }

    public List<Role> dtoToEntity(List<RoleDTO> dto) {
        return dto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}