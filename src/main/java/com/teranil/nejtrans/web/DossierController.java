package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.FormClass.FormClass;
import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.service.DossierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/dossier")
@Api(value="Dossier operations", description="Operations pertaining to folders in Nejtrans Application")
public class DossierController {

    private final DossierService dossierService;

    @ApiOperation(value = "Used by Admin to view a list of all folders")
    @GetMapping("/all")
    public List<DossierDTO> getAll(){
        return dossierService.getAll();
    }

    @ApiOperation(value = "Used by Admin to create a folder for an existing user body : typedossier and username")
    @PostMapping("/save")
    public void saveDossier(@RequestBody FormClass.DossierForm form){
        dossierService.createDossier(form);
    }
    @ApiOperation(value = "Used by Admin to delete a folder by its id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDossier(@PathVariable Long id){
        return dossierService.delete(id);
    }


}
