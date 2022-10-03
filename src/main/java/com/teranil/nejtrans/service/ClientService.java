package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.ToDoRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.mapper.DossierConverter;
import com.teranil.nejtrans.mapper.ToDoConverter;
import com.teranil.nejtrans.mapper.UserConverter;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.ToDo;
import com.teranil.nejtrans.model.Util.HelperClass;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.model.dto.ToDoDTO;
import com.teranil.nejtrans.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final UserConverter userConverter;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

   public ResponseEntity<UserDTO> getCurrentUser(){
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
       return ResponseEntity.ok().body(userConverter.entityToDto(LoggedInUser));
   }

   public ResponseEntity<String> UpdateSignInDetails(String realun,String un , String pass , String currentPass){
       boolean ixeist = userRepository.existsByUsername(un);
       if (ixeist){
           return ResponseEntity.badRequest().body("This Username Alredy Exist choose another one");
       }
       else {
           User user = userRepository.findByUsername(realun);
           if (pass.isEmpty()){
               user.setUsername(un);
               userRepository.flush();
               return ResponseEntity.ok().body("The Username was Successfully Updated");
           }
           else {
               if (!un.isEmpty()){
                   user.setUsername(un);
               }
               if (currentPass.isEmpty()){
                   return ResponseEntity.badRequest().body("Enter Your Current Password");
               }
               if (!bCryptPasswordEncoder.matches(currentPass,user.getPassword())){
                   return ResponseEntity.badRequest().body("Your Current Password is not matches");
               }
               user.setPassword(bCryptPasswordEncoder.encode(pass));
               userRepository.flush();
               return ResponseEntity.ok().body("The Username and Password was Successfully Updated");
           }
       }

   }

    public ResponseEntity<String> UpdateUserDetails(User user){
        User user1 = userRepository.getById(user.getId());

        user1.setEmail(user.getEmail());
        user1.setAddress(user.getAddress());
        user1.setTelephone(user.getTelephone());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEnabled(user.getEnabled());
        userRepository.flush();
        return ResponseEntity.ok().body("Info Details Updating Successfully");

    }

}