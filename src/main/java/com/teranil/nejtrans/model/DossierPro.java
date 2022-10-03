package com.teranil.nejtrans.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DossierPro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime endAt;

    @OneToMany(mappedBy = "dossierPro", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Dossier> dossiers = new ArrayList<>();

    @ManyToOne
    private User user;
}
