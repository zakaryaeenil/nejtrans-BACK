package com.teranil.nejtrans.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserDTO implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String address;
    private String email;
    private String password;
    private Date createdAt;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<DossierDTO> dossier = new ArrayList<>();
    private Collection<RoleDTO> roles = new ArrayList<>();


}
