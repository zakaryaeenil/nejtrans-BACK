package com.teranil.nejtrans.dao;

import com.teranil.nejtrans.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}