package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.NotificationRepository;
import com.teranil.nejtrans.mapper.NotificationConverter;
import com.teranil.nejtrans.model.dto.NotificationDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationConverter notificationConverter;

     public List<NotificationDTO>getAllNotifications(){
         LocalDateTime currentDate=LocalDateTime.now();
        notificationRepository.findAll().forEach(notification -> {
            Period period = Period.between(notification.getCreatedAt().toLocalDate(), currentDate.toLocalDate());
            period = period.minusDays(notification.getCreatedAt().toLocalDate().compareTo(currentDate.toLocalDate()) >= 0 ? 0 : 1);
            Duration duration = Duration.between(notification.getCreatedAt(), currentDate);
            duration = duration.minusDays(duration.toDaysPart());
            int hours = duration.toHoursPart();
            String ishours=hours>1 ? hours +" hours ":" ";
            String seconds=duration.toSecondsPart()+" seconds ";
            String minutes = (duration.toMinutesPart() > 0 ? (duration.toMinutesPart() + " minutes ") : "");
            String days = period.getDays() > 1 ? " days " : " day ";
            String isday = (period.getDays() > 0 ? (period.getDays() + days) : "");
            String isSeconds=hours<1 ? seconds : "";
            notification.setTime(isday + ishours + minutes+ isSeconds +" ago");
        });
        notificationRepository.flush();
        return notificationConverter.entityToDto(notificationRepository.findAll());
    }

}
