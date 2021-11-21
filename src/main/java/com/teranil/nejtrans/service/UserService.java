package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.mapper.DossierConverter;
import com.teranil.nejtrans.mapper.UserConverter;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final DossierConverter dossierConverter;


    public List<UserDTO> getAll() {
        return userConverter.entityToDto(userRepository.findAll());
    }

/*    public void createFolder(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByEmail(auth.getPrincipal().toString());

    }*/
}