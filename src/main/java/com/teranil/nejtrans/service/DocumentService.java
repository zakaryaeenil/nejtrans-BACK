package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DocumentRepository;
import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.model.Document;
import com.teranil.nejtrans.model.Dossier;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class DocumentService {
     private final DocumentRepository documentRepository;
     private final DossierRepository dossierRepository;

     public ResponseEntity<String> uploadFiles(Long id, List<MultipartFile> multipartFile) throws IOException {
        Dossier dossier= dossierRepository.getById(id);

        for(MultipartFile file:multipartFile){
            String filename= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Document document =new Document();
            document.setDossier(dossier);
            document.setTypeDocument("Test ajout document");
            document.setName(filename);
            document.setContent(file.getBytes());
            document.getDossier().setNb_documents(document.getDossier().getNb_documents()+1);
            documentRepository.save(document);
        }

        return ResponseEntity.ok().body("Test");
     }

     public ResponseEntity<String> downloadFile(Long id, HttpServletResponse response) throws Exception {
         Optional<Document> result=documentRepository.findById(id);
         if(result.isEmpty()){
             throw new Exception("Could not find Document with id"+id);
         }
         Document document=result.get();
         response.setContentType("application/octet-stream");
         String headerKey="Content-Disposition";
         String headerValue="attachment; filename="+document.getName();
         response.setHeader(headerKey,headerValue);
         ServletOutputStream outputStream= response.getOutputStream();
         outputStream.write(document.getContent());
         outputStream.close();
        return ResponseEntity.ok().body("testhh");
     }

}
