package com.teranil.nejtrans.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teranil.nejtrans.model.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String address;
    private String email;
    private String password;
    private String telephone;
    private int countDossiers=0;
    private int countReservations=0;

    private LocalDateTime createdAt;
    @JsonIgnore
    private Collection<DossierDTO> dossier = new ArrayList<>();
    private Collection<RoleDTO> roles = new ArrayList<>();
    @JsonIgnore
    private Collection<EventDTO> events = new ArrayList<>();

}
