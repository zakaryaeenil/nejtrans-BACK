package com.teranil.nejtrans.dao;


import com.teranil.nejtrans.model.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByRoles_Id(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);



}
