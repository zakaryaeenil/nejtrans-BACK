package com.teranil.nejtrans.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static com.teranil.nejtrans.model.Util.HelperClass.EnAttente;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DossierDTO implements Serializable {
    private Long id;
    private String typeDossier;
    private Integer nb_documents = 0;
    private Integer available = EnAttente;
    private String employeeUsername="";
    private String etat;
    private String operation;
    private LocalDateTime createdAt;
    private Collection<DocumentDTO> documents = new ArrayList<>();
    private UserDTO user;

}
