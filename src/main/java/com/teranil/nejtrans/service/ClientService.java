package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.ToDoRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.mapper.DossierConverter;
import com.teranil.nejtrans.mapper.ToDoConverter;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.ToDo;
import com.teranil.nejtrans.model.Util.HelperClass;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.model.dto.ToDoDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


import static com.teranil.nejtrans.model.Util.HelperClass.*;

@Service
@Transactional
@AllArgsConstructor
public class ClientService {

    private final UserRepository userRepository;
    private final DossierRepository dossierRepository;
    private final DossierConverter dossierConverter;




public List<DossierDTO> getfolders(String type){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
    List<Dossier> dossiersList;
    switch (type){
        case "All":
            dossiersList=dossierRepository.findByUserId(LoggedInUser.getId());
            break;
        case "Import":
        case "Export":
            dossiersList=dossierRepository.findByUserIdAndTypeDossier(LoggedInUser.getId(),type);
            break;
        case "Entraitement":
            dossiersList=dossierRepository.findByUserIdAndAvailable(LoggedInUser.getId(),EnTraitement);
            break;
        case "Terminer":
            dossiersList=dossierRepository.findByUserIdAndAvailable(LoggedInUser.getId(),Terminer);
            break;

        case "Enattente":
            dossiersList=dossierRepository.findByUserIdAndAvailable(LoggedInUser.getId(),EnAttente);
            break;

        default:
            throw new IllegalStateException("Unexpected value: " + type);
    }

    return dossierConverter.entityToDto(dossiersList);
}
    //Logged in client can get his created folders list
    public ResponseEntity<List<DossierDTO>> getUserFolders(String type) {
        List<DossierDTO> dossiersList=getfolders(type);
        return ResponseEntity.ok().body(dossiersList);
    }

    public ResponseEntity<Integer> getUserFoldersCount(String type){
        List<DossierDTO> dossiersList=getfolders(type);
        return ResponseEntity.ok().body(dossiersList.size());
    }



}