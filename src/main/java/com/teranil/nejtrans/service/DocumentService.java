package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DocumentRepository;
import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.model.Document;
import com.teranil.nejtrans.model.Dossier;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import java.net.MalformedURLException;
import java.nio.file.Files;
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
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DossierRepository dossierRepository;
    public static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/uploads/";

    public ResponseEntity<String> uploadFiles(Long id, List<MultipartFile> multipartFile) throws IOException {
        Dossier dossier = dossierRepository.getById(id);

        for (MultipartFile file : multipartFile) {
            String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path path = get(DIRECTORY, filename).toAbsolutePath().normalize();
            Document document = new Document();
            document.setDossier(dossier);
            document.setTypeDocument("Test ajout document");
            document.setName(filename);
            document.setContent(file.getBytes());
            document.getDossier().setNb_documents(document.getDossier().getNb_documents() + 1);
            copy(file.getInputStream(), path, REPLACE_EXISTING);
            documentRepository.save(document);
        }

        return ResponseEntity.ok().body("Test");
    }

    public ResponseEntity<String> downloadFileFromDB(Long id, HttpServletResponse response) throws Exception {
        Optional<Document> result = documentRepository.findById(id);
        if (result.isEmpty()) {
            throw new Exception("Could not find Document with id" + id);
        }
        Document document = result.get();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + document.getName();
        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(document.getContent());
        outputStream.close();
        return ResponseEntity.ok().body("testhh");
    }

    public ResponseEntity<Resource> downloadFileFromServer(Long id) throws IOException {
        Optional<Document> document = documentRepository.findById(id);
        if (document.isPresent()) {
            Path filepath = get(DIRECTORY).toAbsolutePath().normalize().resolve(document.get().getName());
            if (!Files.exists(filepath)) {
                return ResponseEntity.badRequest().build();
            }

            Resource resource = new UrlResource(filepath.toUri());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("File-name", document.get().getName());
            httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());


            return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filepath)))
                    .headers(httpHeaders).body(resource);
        }
        return ResponseEntity.badRequest().build();

    }
}
