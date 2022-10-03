package com.teranil.nejtrans.dao;


import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.DossierPro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("*")
@RepositoryRestResource
public interface DossierProRepository extends JpaRepository<DossierPro, Long> {

    List<DossierPro> findByUserId(Long id);
}
