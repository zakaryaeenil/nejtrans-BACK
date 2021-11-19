package com.teranil.nejtrans.dao;

import com.teranil.nejtrans.model.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface DossierRepository extends JpaRepository<Dossier,Long> {
List<Dossier> findByTypeDossier(String TypeDossier);
List<Dossier> findByAvailable(Integer available);
}
