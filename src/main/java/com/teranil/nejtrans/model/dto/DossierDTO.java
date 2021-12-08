package com.teranil.nejtrans.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DossierDTO implements Serializable {
    private Long id;
    private String typeDossier;
    private Integer nb_documents = 0;
    private Integer available = 1;
    private String employeeUsername;
    private String etat;
    private LocalDateTime createdAt;
    private Collection<DocumentDTO> documents = new ArrayList<>();
    private UserDTO user;

}
