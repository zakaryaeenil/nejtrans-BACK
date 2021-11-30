package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.EventRepository;
import com.teranil.nejtrans.mapper.EventConverter;
import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.model.dto.EventDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class EventService {
    private final EventConverter eventConverter;
    private final EventRepository eventRepository;

    public ResponseEntity<List<EventDTO>> getAll() {
        return ResponseEntity.ok().body(eventConverter.entityToDto(eventRepository.findAll()));
    }
}
