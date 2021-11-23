package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.mapper.DossierConverter;
import com.teranil.nejtrans.mapper.UserConverter;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.FormClass.FormClass;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final DossierRepository dossierRepository;
    private final DossierConverter dossierConverter;


    public List<UserDTO> getAll() {
        return userConverter.entityToDto(userRepository.findAll());
    }

  public void createFolder(FormClass.DossierForm form){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        Dossier dossier=new Dossier();
        dossier.setTypeDossier(form.getTypeDossier());
        dossier.setUser(LoggedInUser);
        dossierRepository.save(dossier);
    }

    public List<Dossier> getEmployeeFolder(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        return dossierRepository.findByEmployeeUsername(LoggedInUser.getUsername());
    }

    public List<Dossier> getUserFolder(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        return (List<Dossier>) LoggedInUser.getDossier();
    }

    public void bookFolder(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User LoggedInUser = userRepository.findByUsername(auth.getPrincipal().toString());
        Dossier dossier=dossierRepository.getById(id);
        List<Dossier> dossierList=dossierRepository.findByEmployeeUsername("");
        if(dossierList.contains(dossier) && dossier.getAvailable()==1){
            dossier.setEmployeeUsername(LoggedInUser.getUsername());
            dossierRepository.save(dossier);
        }
        else{
            System.out.println("dossier deja reservé!");
        }
    }

    public Integer employeeCompletedFoldersNumber(String empUsername){
        List<Dossier> dossierList=dossierRepository.findByEmployeeUsername(empUsername);
        int count=0;
        for(int i=0;i<dossierList.size();i++){
            if(dossierList.get(i).getAvailable()==3)
                count++;
        }
        return count;
    }

    public Integer employeeFoldersNumber(String empUsername){
        return dossierRepository.findByEmployeeUsername(empUsername).size();
    }

    public Integer userFoldersNumber(String username){
        User user=userRepository.findByUsername(username);
        return user.getDossier().size();
    }

}