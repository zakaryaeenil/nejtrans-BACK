package com.teranil.nejtrans.dao;

import com.teranil.nejtrans.model.Document;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*")
@RepositoryRestResource
public interface DossierRepository extends JpaRepository<Dossier, Long> {
    List<Dossier> findByTypeDossier(String TypeDossier);

    List<Dossier> findByAvailable(Integer available);

    List<Dossier> findByEmployeeUsername(String username);

    List<Dossier> findByTypeDossierAndUser(String TypeDossier, User user);

    List<Dossier> findByAvailableAndUserId(Integer available,Long id);

    List<Dossier> findByEmployeeUsernameAndTypeDossier(String username, String TypeDossier);

    List<Dossier> findByUserId(Long id);

    List<Dossier> findByUserIdAndTypeDossier(Long id,String typeDossier);

    List<Dossier> findByUserIdAndAvailable(Long id,Integer available);

    List<Dossier> findByEmployeeUsernameAndAvailable(String username,int available );

}
