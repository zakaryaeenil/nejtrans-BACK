package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.mapper.DossierConverter;
import com.teranil.nejtrans.mapper.UserConverter;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.FormClass.FormClass;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.DossierDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class ClientService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final DossierRepository dossierRepository;
    private final DossierConverter dossierConverter;

    //Logged in Client can create a folder of documents
    public ResponseEntity<String> createFolder(FormClass.DossierForm form) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        if (Objects.isNull(LoggedInUser) && (Objects.isNull(form))) {
            return ResponseEntity.badRequest().body("Please insert Type,or login");
        }
        Dossier dossier = new Dossier();
        dossier.setTypeDossier(form.getTypeDossier());
        dossier.setUser(LoggedInUser);
        LoggedInUser.setCountDossiers(LoggedInUser.getCountDossiers()+1);
        dossierRepository.save(dossier);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/createfoler").toUriString());
        return ResponseEntity.created(uri).body("Dossier created succesfully");
    }


    //Logged in client can get his created folders list
    public ResponseEntity<List<DossierDTO>> getUserFolders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        return ResponseEntity.ok().body(dossierConverter.entityToDto((List<Dossier>) LoggedInUser.getDossier()));
    }


}