package com.teranil.nejtrans.dao;

import com.teranil.nejtrans.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
