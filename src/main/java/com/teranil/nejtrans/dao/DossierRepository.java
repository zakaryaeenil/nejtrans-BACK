package com.teranil.nejtrans.dao;

import com.teranil.nejtrans.model.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DossierRepository extends JpaRepository<Dossier,Long> {

}
