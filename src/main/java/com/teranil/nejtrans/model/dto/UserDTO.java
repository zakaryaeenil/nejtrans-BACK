package com.teranil.nejtrans.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teranil.nejtrans.model.DossierPro;

import com.teranil.nejtrans.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;


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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String telephone;
    private int countDossiers=0;
    private int countReservations=0;
    private Boolean enabled=true;

    private LocalDateTime createdAt;
    @JsonIgnore
    private Collection<DossierDTO> dossier = new ArrayList<>();
    @JsonIgnore
    private Collection<RoleDTO> roles = new ArrayList<>();
    @JsonIgnore
    private Collection<EventDTO> events = new ArrayList<>();
    @JsonIgnore
    private Collection<DossierPro> dossierPros = new ArrayList<>();

    @JsonIgnore
    private Collection<NotificationDTO> notifications = new ArrayList<>();
}
