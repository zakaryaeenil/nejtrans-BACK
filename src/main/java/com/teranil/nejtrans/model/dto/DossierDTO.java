package com.teranil.nejtrans.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DossierDTO implements Serializable {
    private Long id;
    private String typeDossier;
    private Integer nb_documents=0;
    private Integer available=1;
    private String reservation_username;
    private String etat;
    private Date createdAt;
    private Collection<DocumentDTO> documents=new ArrayList<>();
    private UserDTO user;

}
