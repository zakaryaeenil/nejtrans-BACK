package com.teranil.nejtrans.mapper;



import com.teranil.nejtrans.model.Notification;
import com.teranil.nejtrans.model.dto.NotificationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationConverter {

    public NotificationDTO entityToDto(Notification notification) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(notification, NotificationDTO.class);
    }

    public List<NotificationDTO> entityToDto(List<Notification> notifications) {

        return notifications.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public Collection<NotificationDTO> entityToDto(Collection<Notification> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }


    public Notification dtoToEntity(NotificationDTO dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Notification.class);

    }

    public List<Notification> dtoToEntity(List<NotificationDTO> dto) {
        return dto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}