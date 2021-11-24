package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.FormClass.FormClass;
import com.teranil.nejtrans.model.dto.UserDTO;
import com.teranil.nejtrans.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
@Api(value="User operations", description="Operations pertaining to clients in Nejtrans Application")

public class UserController {

    public final UserService userService;


    @ApiOperation(value = "Used by Admin to view a list of all users")
    @GetMapping("/all")
    public List<UserDTO> getAll(){
        return userService.getAll();
    }

    @ApiOperation(value = "Used by Logged in client to creater a Folder")
    @PostMapping("/createfolder")
    public void getAll(@RequestBody FormClass.DossierForm form){
        userService.createFolder(form);
    }


    @ApiOperation(value = "Used by Logged in employee to get reserved folder")
    @GetMapping("/employeefolder")
    public List<Dossier> getEmployeeFolders(){
        return userService.getEmployeeFolder();
    }

    @ApiOperation(value = "Used by Logged in client to get his created Folders list")
    @GetMapping("/userfolder")
    public List<Dossier> getUserFolders(){
        return userService.getUserFolder();
    }

    @ApiOperation(value = "Used by Logged in employee to reserve a Folder")
    @PutMapping("/book/{id}")
    public void bookFolder(@PathVariable Long id){
        userService.bookFolder(id);
    }

    @ApiOperation(value = "Used by Admin to get how many folders has a specific employee completed ")
    @GetMapping("/empcompfoldernumber/{username}")
    public int getEmpCompletedFoldersNumber(@PathVariable String username){
        return userService.employeeCompletedFoldersNumber(username);
    }
    @ApiOperation(value = "Used by Admin to get how many folders is a specific employee working or worked on regardless on completion ")
    @GetMapping("/empfoldernumber/{username}")
    public int getEmpFoldersNumber(@PathVariable String username){
        return userService.employeeFoldersNumber(username);
    }


    @ApiOperation(value = "Used by Admin to get a list of folders for a specific user  ")
    @GetMapping("/{id}/dossiers")
    public List<Dossier> getUserFolders(@PathVariable Long id){
            return userService.adminGetUserFolderList(id);
    }
    @ApiOperation(value = "Used by Admin to get how an integer of how many folders has a specific user created ")
    @GetMapping("/userfoldernumber/{username}")
    public int getUserFoldersNumber(@PathVariable String username){
        return userService.userFoldersNumber(username);
    }


    @ApiOperation(value = "Used by Admin to get a list of folders by type for a specific user ")
    @GetMapping("/dossiers/{type}")
    public List<Dossier> getUserFolderByType(@PathVariable Long id,@PathVariable String type){
        return userService.dossierTypeForUser(id,type);
    }
}
