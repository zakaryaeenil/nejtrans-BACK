package com.teranil.nejtrans.model.dto;

import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DossierProDTO {

    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime endAt;

    private Collection<DossierDTO> dossiers = new ArrayList<>();
    private UserDTO user;
}
