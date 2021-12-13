package com.teranil.nejtrans.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoDTO {
    private Long id;
    private String description;
    private String title;
    private LocalDateTime createdAt;
    private String type;
    private UserDTO user;
}
