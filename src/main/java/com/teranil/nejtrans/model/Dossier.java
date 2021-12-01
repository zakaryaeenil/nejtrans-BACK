package com.teranil.nejtrans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dossier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeDossier;
    private Integer nb_documents = 0;
    private Integer available = 1;
    private String employeeUsername = "";
    private String etat;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "doc_dossier", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Document> documents = new ArrayList<>();
    @ManyToOne
    private User user;

}
