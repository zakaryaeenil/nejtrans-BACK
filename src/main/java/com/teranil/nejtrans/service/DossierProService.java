package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DossierProRepository;
import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.NotificationRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.mapper.DossierProConverter;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.DossierPro;
import com.teranil.nejtrans.model.Notification;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.Util.HelperClass;
import com.teranil.nejtrans.model.dto.DossierProDTO;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class DossierProService {

    private final DossierProRepository dossierProRepository;
    private final UserRepository userRepository;
    private final DossierRepository dossierRepository;
    private final NotificationRepository notificationRepository;
    private final DossierProConverter dossierProConverter;

    public ResponseEntity<String> createDossierPro(HelperClass.DossierProForm form) throws IOException {

        DossierPro dossier = new DossierPro();
        Notification notification = new Notification();

        User user =userRepository.getById(form.getUser_id());
        for (DossierPro dossierPro : user.getDossierPros()) {
            if (dossierPro.getCreatedAt().isAfter(form.getCreatedAt())  && dossierPro.getEndAt().isBefore(form.getEndAt())){
                return ResponseEntity.badRequest().body("Already Exist a Dossier Pro");
            }
        }


        dossier.setCreatedAt(form.getCreatedAt());
        dossier.setEndAt(form.getEndAt());
        dossier.setUser(user);
        dossier.setName(user.getUsername() +"-"+ dossier.getCreatedAt().getMonth().toString() +"-"+ dossier.getCreatedAt().getYear());
        dossierProRepository.save(dossier);
        notification.setDescription(user.getUsername()+" has created folder "+ dossier.getId());
        notificationRepository.save(notification);

         return ResponseEntity.ok().body("Created successfully");
    }

    public ResponseEntity<List<DossierProDTO>> getAll(Long id) throws IOException {
          List<DossierPro> dossierPros = dossierProRepository.findByUserId(id);
        return ResponseEntity.ok().body(dossierProConverter.entityToDto(dossierPros));
    }

    public ResponseEntity<String> addToDossierPro(HelperClass.AddDossierProForm form) throws IOException {

        Notification notification = new Notification();

        Dossier dossier =dossierRepository.getById(form.getFolder_id());
        DossierPro dossierPro =dossierProRepository.getById(form.getFolder_Pro_id());
        for (Dossier file : dossierPro.getDossiers()) {
            if (file.equals(dossier)){
                return ResponseEntity.ok().body("Already Added to this ");
            }
        }
        dossier.setDossierPro(dossierPro);
        dossierRepository.save(dossier);
        notification.setDescription(dossier.getId()+" has added to Folder Pro "+ dossierPro.getId());
        notificationRepository.save(notification);

        return ResponseEntity.ok().body("Added  successfully");
    }

}
