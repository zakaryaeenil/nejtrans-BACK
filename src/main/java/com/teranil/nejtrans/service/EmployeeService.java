package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.mapper.DossierConverter;
import com.teranil.nejtrans.mapper.UserConverter;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.DossierDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.teranil.nejtrans.model.Util.HelperClass.EnTraitement;
import static com.teranil.nejtrans.model.Util.HelperClass.Terminer;

@Service
@Transactional
@AllArgsConstructor
public class EmployeeService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final DossierRepository dossierRepository;
    private final DossierConverter dossierConverter;
    private final MailSenderService mailSender;

    //Logged in employee can see his reserved folders list and history
    public ResponseEntity<List<DossierDTO>> getEmployeeFolders(String type) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        List<Dossier> dossiersList;
        switch (type){
            case "All":
                dossiersList=dossierRepository.findByEmployeeUsername(LoggedInUser.getUsername());
                System.out.println(dossiersList.size());
                System.out.println(LoggedInUser.getUsername());
                break;
            case "Import":
            case "Export":
                dossiersList=dossierRepository.findByEmployeeUsernameAndTypeDossier(LoggedInUser.getUsername(), type);
                break;
            case "Entraitement":
                dossiersList=dossierRepository.findByEmployeeUsernameAndAvailable(LoggedInUser.getUsername(),EnTraitement);
                break;
            case "Terminer":
                dossiersList=dossierRepository.findByEmployeeUsernameAndAvailable(LoggedInUser.getUsername(),Terminer);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        return ResponseEntity.ok().body(dossierConverter.entityToDto(dossiersList));

    }

    //Logged in Employee can reserve an existing folder
    public ResponseEntity<String> bookFolder(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        Dossier dossier = dossierRepository.getById(id);
        List<Dossier> dossierList = dossierRepository.findByEmployeeUsername("");
        if (dossierList.contains(dossier) && dossier.getAvailable() == 1) {
            dossier.setEmployeeUsername(LoggedInUser.getUsername());
            dossierRepository.save(dossier);
            List<User> users=userRepository.findByRoles_Id(1L);
            String body="employee"+dossier.getEmployeeUsername()+" has reserved folder "+dossier.getId()+"Type : "+dossier.getTypeDossier()+" at "+ LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm"));
            for (User user : users) {
                try {
                    mailSender.SendEmail(user.getEmail(),"Folder has been reserved!",body);
                } catch (MailException mailException) {
                    mailException.printStackTrace();
                }
            }

            LoggedInUser.setCountReservations(LoggedInUser.getCountReservations()+1);
            return ResponseEntity.ok().body("booked successfully ");
        } else {
            return ResponseEntity.badRequest().body("Error during reservation !");
        }
    }

    public ResponseEntity<List<DossierDTO>> getNonReservedFolders(){
        List<Dossier> dossierList = dossierRepository.findByEmployeeUsername("");
        return ResponseEntity.ok().body(dossierConverter.entityToDto(dossierList));
    }


}
