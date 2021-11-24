package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.FormClass.FormClass;
import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.model.dto.UserDTO;
import com.teranil.nejtrans.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "*")
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

    @PutMapping("/api/users/book/{id}")
    public void bookFolder(@PathVariable Long id){
        userService.bookFolder(id);
    }

    @GetMapping("/api/users/empcompfoldernumber/{username}")
    public int getEmpCompletedFoldersNumber(@PathVariable String username){
        return userService.employeeCompletedFoldersNumber(username);
    }

    @GetMapping("/api/users/empfoldernumber/{username}")
    public int getEmpFoldersNumber(@PathVariable String username){
        return userService.employeeFoldersNumber(username);
    }

    @GetMapping("/api/users/{id}/dossiers")
    public List<Dossier> getUserFolders(@PathVariable Long id){
            return userService.adminGetUserFolderList(id);
    }

    @GetMapping("/api/users/userfoldernumber/{username}")
    public int getuserFoldersNumber(@PathVariable String username){
        return userService.userFoldersNumber(username);
    }
}
