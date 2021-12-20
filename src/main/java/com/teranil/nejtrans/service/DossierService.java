package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DocumentRepository;
import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.mapper.DossierConverter;
import com.teranil.nejtrans.model.Document;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.Util.HelperClass;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.DossierDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Transactional
@AllArgsConstructor
public class DossierService {

    private final DossierRepository dossierRepository;
    private final UserRepository userRepository;
    private final DossierConverter dossierConverter;
    private final DocumentRepository documentRepository;
    public static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads";


    public ResponseEntity<List<DossierDTO>> getAll() {
        return ResponseEntity.ok().body(dossierConverter.entityToDto(dossierRepository.findAll()));
    }

    public ResponseEntity<String> createDossier(HelperClass.DossierForm form, List<MultipartFile> multipartFile) throws IOException {
        Dossier dossier = new Dossier();
        dossier.setTypeDossier(form.getTypeDossier());
        User user = userRepository.findByUsername(form.getUsername());
        if (Objects.isNull(user)) {
            return ResponseEntity.badRequest().body("Error while creating folder");
        }
        dossier.setUser(user);
        dossier.setOperation(form.getOperation());
        dossierRepository.save(dossier);

        for (MultipartFile file : multipartFile) {
            String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path path = get(DIRECTORY, filename).toAbsolutePath().normalize();
            Document document = new Document();
            document.setDossier(dossier);
            document.setTypeDocument(file.getContentType());
            document.setName(filename);
            document.setContent(file.getBytes());
            document.getDossier().setNb_documents(document.getDossier().getNb_documents() + 1);
            copy(file.getInputStream(), path, REPLACE_EXISTING);
            documentRepository.save(document);

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
