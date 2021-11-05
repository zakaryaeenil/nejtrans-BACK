package com.teranil.nejtrans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dossier implements Serializable {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String typeDossier;
    private Integer nb_documents;
    private boolean available;
    private String reservation_username;
    private String etat;
    @OneToMany(mappedBy = "doc_dossier")
    private Collection<Document> documents=new ArrayList<>();
    @ManyToOne
     private User user;

}
