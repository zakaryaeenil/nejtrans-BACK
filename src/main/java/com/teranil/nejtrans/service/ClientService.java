package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.mapper.DossierConverter;
import com.teranil.nejtrans.mapper.UserConverter;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.Util.HelperClass;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.DossierDTO;
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
    private final UserConverter userConverter;
    private final DossierRepository dossierRepository;
    private final DossierConverter dossierConverter;
    private final MailSenderService mailSender;

    //Logged in Client can create a folder of documents
    public ResponseEntity<String> createFolder(HelperClass.DossierForm form) {
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
        List<User> users=userRepository.findByRoles_Id(1L);
        String body="User "+dossier.getUser().getUsername()+" has created folder "+dossier.getId()+" "+dossier.getTypeDossier()+" at "+dossier.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm"));
        for (User user : users) {
            try {
                mailSender.SendEmail(user.getEmail(),"New folder has been created!",body);
            } catch (MailException mailException) {
                mailException.printStackTrace();
            }
        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/client/createfoler").toUriString());
        return ResponseEntity.created(uri).body("Dossier created succesfully");
    }


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