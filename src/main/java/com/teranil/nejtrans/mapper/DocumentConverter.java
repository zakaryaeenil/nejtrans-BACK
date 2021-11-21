package com.teranil.nejtrans.mapper;


import com.teranil.nejtrans.model.Document;
import com.teranil.nejtrans.model.dto.DocumentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class DocumentConverter {
    public DocumentDTO entityToDto(Document doc) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(doc, DocumentDTO.class);
    }

    public List<DocumentDTO> entityToDto(List<Document> docs) {

        return docs.stream().map(this::entityToDto).collect(Collectors.toList());

    }



    public Document dtoToEntity(DocumentDTO dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Document.class);

    }

    public List<Document> dtoToEntity(List<DocumentDTO> dto) {

        return dto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
