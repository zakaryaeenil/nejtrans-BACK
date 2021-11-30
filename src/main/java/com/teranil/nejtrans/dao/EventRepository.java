package com.teranil.nejtrans.dao;

import com.teranil.nejtrans.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RepositoryRestResource
public interface EventRepository extends JpaRepository<Event, Long> {

}