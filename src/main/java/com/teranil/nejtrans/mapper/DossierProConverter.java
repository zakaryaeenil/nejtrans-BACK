package com.teranil.nejtrans.mapper;

import com.teranil.nejtrans.model.DossierPro;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.DossierProDTO;
import com.teranil.nejtrans.model.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DossierProConverter {

    public DossierProDTO entityToDto(DossierPro dossierPro) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dossierPro, DossierProDTO.class);
    }

    public List<DossierProDTO> entityToDto(List<DossierPro> dossierPros) {

        return dossierPros.stream().map(this::entityToDto).collect(Collectors.toList());

    }

    public Collection<DossierProDTO> entityToDto(Collection<DossierPro> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());

    }

    public DossierPro dtoToEntity(DossierProDTO dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, DossierPro.class);

    }

    public List<DossierPro> dtoToEntity(List<DossierProDTO> dto) {
        return dto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
