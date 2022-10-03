package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.DossierPro;
import com.teranil.nejtrans.model.Util.HelperClass;
import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.model.dto.DossierProDTO;
import com.teranil.nejtrans.service.DossierProService;
import com.teranil.nejtrans.service.DossierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/dossierpro")
@Api(value = "Admin operations", description = "Operations pertaining to Admins in Nejtrans Application")
public class DossierProController {

    private final DossierProService dossierProService;


    @ApiOperation(value = "Used by Admin to view a list of all folders Pro by user ID")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<DossierProDTO>> getAll(@PathVariable long id) throws IOException {
        return dossierProService.getAll(id);
    }

    @ApiOperation(value = "Used by Admin to create a folder for an existing user body : typedossier and username")
    @PostMapping("/save")
    public ResponseEntity<String> saveDossierPro(@RequestBody HelperClass.DossierProForm form ) throws IOException {
        return dossierProService.createDossierPro(form);
    }
    @ApiOperation(value = "Used by Admin to create a folder for an existing user body : typedossier and username")
    @PostMapping("/addtodossierpro")
    public ResponseEntity<String> AddToDossierPro(@RequestBody HelperClass.AddDossierProForm form ) throws IOException {
        return dossierProService.addToDossierPro(form);
    }

}
