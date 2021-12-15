package com.teranil.nejtrans.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO implements Serializable {
    private Long id;
    private String name;
    private String typeDocument;
    private LocalDateTime uploadDate;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] content;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private DossierDTO dossier;
}
