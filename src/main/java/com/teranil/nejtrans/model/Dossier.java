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

import static com.teranil.nejtrans.model.Util.HelperClass.EnAttente;


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
    private Integer available = EnAttente;
    private String employeeUsername = "";
    private String etat;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Document> documents = new ArrayList<>();
    @ManyToOne
    private User user;

}
