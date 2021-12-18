package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.mapper.DossierConverter;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.Util.HelperClass;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.DossierDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class DossierService {

    private final DossierRepository dossierRepository;
    private final UserRepository userRepository;
    private final DossierConverter dossierConverter;


    public ResponseEntity<List<DossierDTO>> getAll() {
        return ResponseEntity.ok().body(dossierConverter.entityToDto(dossierRepository.findAll()));
    }

    public ResponseEntity<String> createDossier(HelperClass.DossierForm form) {
        Dossier dossier = new Dossier();
        dossier.setTypeDossier(form.getTypeDossier());
        User user = userRepository.findByUsername(form.getUsername());
        if (Objects.isNull(user)) {
            return ResponseEntity.badRequest().body("Error while creating folder");
        }
        dossier.setUser(user);
        dossier.setOperation(form.getOperation());
        dossierRepository.save(dossier);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/dossier/save").toUriString());
        return ResponseEntity.created(uri).body("Created successfully");
    }

    public ResponseEntity<String> delete(Long id) {
        Optional<Dossier> dossier = dossierRepository.findById(id);
        if (dossier.isEmpty()) {
            return ResponseEntity.badRequest().body("Dossier not found");
        }
        dossierRepository.delete(dossier.get());
        return ResponseEntity.ok().body("Dossier deleted succesfully");
    }

}
