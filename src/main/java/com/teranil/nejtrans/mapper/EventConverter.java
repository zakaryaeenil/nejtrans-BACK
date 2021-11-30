package com.teranil.nejtrans.mapper;


import com.teranil.nejtrans.model.Event;
import com.teranil.nejtrans.model.dto.EventDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventConverter {

    public EventDTO entityToDto(Event event) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(event, EventDTO.class);
    }

    public List<EventDTO> entityToDto(List<Event> events) {

        return events.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Collection<EventDTO> entityToDto(Collection<Event> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }


    public Event dtoToEntity(EventDTO dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Event.class);

    }

    public List<Event> dtoToEntity(List<EventDTO> dto) {
        return dto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}