package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.FormClass.FormClass;
import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.model.dto.UserDTO;
import com.teranil.nejtrans.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    public final UserService userService;

    @GetMapping("/api/users/all")
    public List<UserDTO> getAll(){
        return userService.getAll();
    }

    @PostMapping("/api/users/createfolder")
    public void getAll(@RequestBody FormClass.DossierForm form){
        userService.createFolder(form);
    }

    @GetMapping("/api/users/employeefolder")
    public List<Dossier> getEmployeeFolders(){
        return userService.getEmployeeFolder();
    }
    @GetMapping("/api/users/userfolder")
    public List<Dossier> getUserFolders(){
        return userService.getUserFolder();
    }
}
