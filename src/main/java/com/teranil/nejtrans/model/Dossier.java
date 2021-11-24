package com.teranil.nejtrans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dossier implements Serializable {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String typeDossier;
    private Integer nb_documents=0;
    private Integer available=1;
    private String employeeUsername="";
    private String etat;
    @CreationTimestamp
    private Date createdAt;
    @OneToMany(mappedBy = "doc_dossier",cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Document> documents=new ArrayList<>();
    @JsonIgnore
    @ManyToOne
     private User user;

}
