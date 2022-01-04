package com.teranil.nejtrans.web;

import com.teranil.nejtrans.service.DocumentService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
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
    public ResponseEntity<String> uploadDocument(@PathVariable Long id, @RequestParam("document") List<MultipartFile> multipartFile) throws IOException {
        return documentService.uploadFiles(id, multipartFile);
    }

    @GetMapping("/{id}/downloadDB")
    public ResponseEntity<String> downloadDocument(@PathVariable Long id, HttpServletResponse response) throws Exception {
         return documentService.downloadFileFromDB(id, response);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadDocumentSrv(@PathVariable Long id, HttpServletResponse response) throws Exception {
        return documentService.downloadFileFromServer(id);
    }

    @ApiOperation(value = "Used by Admin to delete a folder by its id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id) {
        return documentService.delete(id);
    }


}
