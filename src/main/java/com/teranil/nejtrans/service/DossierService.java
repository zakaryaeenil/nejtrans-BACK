package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.mapper.DossierConverter;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.FormClass.FormClass;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.DossierDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


   public List<DossierDTO> getAll(){
        return dossierConverter.entityToDto(dossierRepository.findAll());
    }

    public void createDossier(FormClass.DossierForm form){
       Dossier dossier=new Dossier();
       dossier.setTypeDossier(form.getTypeDossier());
       User user= userRepository.findByUsername(form.getUsername());
       if(user==null){
           System.out.println("user not found");
           return;
       }
       dossier.setUser(user);
       dossierRepository.save(dossier);
    }

    public ResponseEntity<String> delete(Long id){
       Optional<Dossier> dossier=dossierRepository.findById(id);
       if(dossier.isEmpty()){
           return ResponseEntity.badRequest().body("Dossier not found");
       }
       dossierRepository.delete(dossier.get());
        return ResponseEntity.ok().body("Dossier deleted succesfully");
    }

}
