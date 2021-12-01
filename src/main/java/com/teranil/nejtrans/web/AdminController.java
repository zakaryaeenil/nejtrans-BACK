package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.FormClass.FormClass;
import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.model.dto.UserDTO;
import com.teranil.nejtrans.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
@Api(value = "Admin operations", description = "Operations pertaining to Admins in Nejtrans Application")
public class AdminController {

    private final AdminService adminService;

    @ApiOperation(value = "Used by Admin to view a list of all users")
    @GetMapping("/users/all")
    public ResponseEntity<List<UserDTO>> getAll() {
        return adminService.getAll();
    }

    @ApiOperation(value = "Used by Admin to get a list of folders that a specific employee has completed ")
    @GetMapping("/employee/{username}/completedfolders")
    public ResponseEntity<List<DossierDTO>> getEmpCompletedFoldersNumber(@PathVariable String username) {
        return adminService.getEmployeeCompletedFolders(username);
    }


    @ApiOperation(value = "Used by Admin to get how a list of folders that a specific employee is working or worked on regardless on completion ")
    @GetMapping("/employee/{username}/folders")
    public ResponseEntity<List<DossierDTO>> getEmpFoldersNumber(@PathVariable String username) {
        return adminService.getEmployeeFolders(username);
    }



    @ApiOperation(value = "Used by Admin to get how a list of folders that a specific user created ")
    @GetMapping("/users/{username}/folders")
    public ResponseEntity<List<DossierDTO>> getUserFoldersByUsername(@PathVariable String username) {
        return adminService.userFoldersByUsername(username);
    }



    @ApiOperation(value = "Used by Admin to get a list of folders for a specific user  ")
    @GetMapping("/user/{id}/folders")
    public ResponseEntity<Collection<DossierDTO>> getUserFolders(@PathVariable Long id) {
        return adminService.getUserFolderList(id);
    }



        @ApiOperation(value = "Used by Admin to get count of folders by year and month for a specific user  ")
        @GetMapping("/user/{id}/folders/{year}")
    public Collection<FormClass.DossierByUserAndYear> getUserFoldersByYear(@PathVariable Long id,@PathVariable int year) {
            return adminService.getUserFoldersListByYear(id,year);
        }



    @ApiOperation(value = "Used by Admin to get a list of folders by type for a specific user ")
    @GetMapping("/user/{id}/dossiers/{type}")
    public ResponseEntity<List<DossierDTO>> getUserFolderByType(@PathVariable Long id, @PathVariable String type) {
        return adminService.getFoldersListByTypeForUser(id, type);
    }

}
