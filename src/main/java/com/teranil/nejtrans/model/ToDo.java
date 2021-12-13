package com.teranil.nejtrans.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String title;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private String type;

    @ManyToOne
    private User user;

}
