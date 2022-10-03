package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.dto.NotificationDTO;
import com.teranil.nejtrans.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private NotificationService notificationService;

    @GetMapping("/all")
    public List<NotificationDTO> getAll(){
        return notificationService.getAllNotifications();
    }

    @PutMapping("/{id}/read")
    public void setRead(@PathVariable Long id){
        notificationService.setRead(id);
    }
}
