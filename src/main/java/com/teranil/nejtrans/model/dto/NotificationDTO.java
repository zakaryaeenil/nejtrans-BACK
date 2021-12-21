package com.teranil.nejtrans.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO implements Serializable {
    private Long id;
    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private Boolean read=false;
    private String time;
}
