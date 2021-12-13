package com.teranil.nejtrans.dao;

import com.teranil.nejtrans.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*")
@RepositoryRestResource
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findByType(String type);
    List<ToDo> findByUserIdAndType(Long id,String type);
    List<ToDo> findByUserId(Long id);


}