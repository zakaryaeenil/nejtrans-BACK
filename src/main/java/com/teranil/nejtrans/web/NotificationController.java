package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.dto.NotificationDTO;
import com.teranil.nejtrans.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private NotificationService notificationService;

    @GetMapping("/all")
    public List<NotificationDTO> getAll(){
        return notificationService.getAllNotifications();
    }
}
