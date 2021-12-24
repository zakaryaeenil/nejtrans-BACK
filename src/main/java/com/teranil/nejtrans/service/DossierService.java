package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DocumentRepository;
import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.NotificationRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.mapper.DossierConverter;
import com.teranil.nejtrans.model.Document;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.Notification;
import com.teranil.nejtrans.model.Util.HelperClass;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.DossierDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.nio.file.Files.copy;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Transactional
@AllArgsConstructor
public class DossierService {

    private final DossierRepository dossierRepository;
    private final UserRepository userRepository;
    private final DossierConverter dossierConverter;
    private final DocumentRepository documentRepository;
    private final NotificationRepository notificationRepository;
    public static final String DIRECTORY = System.getProperty("user.home") + "/Documents/uploads";


    public ResponseEntity<List<DossierDTO>> getAll() {
        return ResponseEntity.ok().body(dossierConverter.entityToDto(dossierRepository.findAll()));
    }

    public ResponseEntity<String> createDossier(HelperClass.DossierForm form, List<MultipartFile> multipartFile) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        Dossier dossier = new Dossier();
        Notification notification = new Notification();
        if(Objects.equals(form.getUsername(), "")){
            dossier.setUser(LoggedInUser);

        }
        else {
            User user = userRepository.findByUsername(form.getUsername());
            if (Objects.isNull(user)) {
                return ResponseEntity.badRequest().body("Error username not found !");
            }
            dossier.setUser(user);
        }
        dossier.setTypeDossier(form.getTypeDossier());
        dossier.setOperation(form.getOperation());
        dossierRepository.save(dossier);
        notification.setDescription(dossier.getUser().getUsername()+" has created folder "+ dossier.getId());
        notificationRepository.save(notification);

        for (MultipartFile file : multipartFile) {
            String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            File folder = new File(DIRECTORY +"/"+ dossier.getId().toString()+"-"+dossier.getUser().getUsername());
            if(folder.mkdir()){
                System.out.println("dossier created");
            }
            else{
                System.out.println("error creating folder");
            }
            {
                Path path = Paths.get(String.valueOf(folder), filename).toAbsolutePath().normalize();
                Document document = new Document();
                document.setDossier(dossier);
                document.setTypeDocument(file.getContentType());
                document.setName(filename);
                document.setContent(file.getBytes());
                document.getDossier().setNb_documents(document.getDossier().getNb_documents() + 1);
                copy(file.getInputStream(), path, REPLACE_EXISTING);
                documentRepository.save(document);

            }

        }

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
