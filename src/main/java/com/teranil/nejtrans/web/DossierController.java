package com.teranil.nejtrans.web;

import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.model.Dossier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DossierController {

    @Autowired
    private DossierRepository dossierRepository;

    @GetMapping("/doss")
    public List<Dossier> getAll(){
        return dossierRepository.findAll();
    }
}
