package com.teranil.nejtrans.web;

import com.teranil.nejtrans.service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

     @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadDocument(@PathVariable Long id, @RequestParam("document") List<MultipartFile> multipartFileList) throws IOException {
        return documentService.uploadFiles(id, multipartFileList);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<String> downloadDocument(@PathVariable Long id, HttpServletResponse response) throws Exception {
         return documentService.downloadFile(id, response);
    }
}
