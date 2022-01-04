package com.teranil.nejtrans.dao;

import com.teranil.nejtrans.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "https://zakaryaeenil.github.io")
@RepositoryRestResource
public interface DocumentRepository extends JpaRepository<Document, Long> {

}
