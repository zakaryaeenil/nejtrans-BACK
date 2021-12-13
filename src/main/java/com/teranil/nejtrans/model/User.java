package com.teranil.nejtrans.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(max = 40, message = "First name is too long")
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Length(max = 40, message = "Last name is too long")
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(nullable = false, unique = true)

    private String username;
    private String address;
    private String telephone;
    private int countDossiers=0;
    private int countReservations=0;
    private Boolean enabled=true;
    @Column(nullable = false, unique = true)
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password should have a minimum of 6 characters")
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Role> roles = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user")
    private Collection<ToDo> toDos=new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user")
    private Collection<Dossier> dossier = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "eventUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Event> events = new ArrayList<>();



}
