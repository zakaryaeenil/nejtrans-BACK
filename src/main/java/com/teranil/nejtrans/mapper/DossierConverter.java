package com.teranil.nejtrans.mapper;

import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.dto.DossierDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class DossierConverter {

    public DossierDTO entityToDto(Dossier dossier) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dossier, DossierDTO.class);
    }

    public List<DossierDTO> entityToDto(List<Dossier> dossiers) {

        return dossiers.stream().map(this::entityToDto).collect(Collectors.toList());

    }



    public Dossier dtoToEntity(DossierDTO dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Dossier.class);

    }

    public List<Dossier> dtoToEntity(List<DossierDTO> dto) {

        return dto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
