package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.FormClass.FormClass;
import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.service.DossierService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class DossierController {

    private final DossierService dossierService;

    @GetMapping("/api/dossier/all")
    public List<DossierDTO> getAll(){
        return dossierService.getAll();
    }

    @PostMapping("/api/dossier/save")
    public void saveDossier(@RequestBody FormClass.DossierForm form){
        dossierService.createDossier(form);
    }

    @DeleteMapping("/api/dossier/{id}")
    public ResponseEntity<String> deleteDossier(@PathVariable Long id){
        return dossierService.delete(id);
    }


}
