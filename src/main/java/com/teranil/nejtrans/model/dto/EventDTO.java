package com.teranil.nejtrans.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO implements Serializable {
    private Long id;
    private String Title;
    private String Description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UserDTO eventUser;

}
