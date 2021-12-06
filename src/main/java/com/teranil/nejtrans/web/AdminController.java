package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.Dossier;
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


    @ApiOperation(value = "Used by Admin to get how a  count of folders that a specific employee is working or worked on regardless on completion by Folder type")
    @GetMapping("/employee/{username}/folders/{type}")
    public ResponseEntity<Integer> getEmpFoldersNumberByType(@PathVariable String username,@PathVariable String type) {
        return adminService.getEmployeeFoldersCountByType(username,type);
    }

    @GetMapping("/employee/{username}/count/{type}/{year}")
    public ResponseEntity<Integer> getEmpFoldersNumberByTypeAndYear(@PathVariable String username,@PathVariable String type,@PathVariable int year) {
        return adminService.getEmployeeFoldersCountByTypeAndYear(username,type,year);
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
        @GetMapping("/employee/{username}/folders/{available}/{year}")
        public Collection<FormClass.DossierByUserAndYear> getEmpFoldersByAvailableAndYear(@PathVariable String username,@PathVariable int available,@PathVariable int year){
        return adminService.getEmpFoldersListByYear(year,username,available);
        }
@PostMapping("/user/create/role/{id}")
public void CreateUser(@RequestBody UserDTO userDTO,@PathVariable Long id){
         adminService.CreateUser(userDTO,id);
}

    @PostMapping("/event/create")
    public ResponseEntity<String> CreateEvent(@RequestBody FormClass.UserEventForm form){
        return adminService.CreateEvent(form);
    }

    @ApiOperation(value = "Used by Admin to get a list of folders by type for a specific user ")
    @GetMapping("/user/{id}/dossiers/{type}")
    public ResponseEntity<List<DossierDTO>> getUserFolderByType(@PathVariable Long id, @PathVariable String type) {
        return adminService.getFoldersListByTypeForUser(id, type);
    }

    @GetMapping("/dossiers/count/{type}")
    public ResponseEntity<Integer> CountFolders(@PathVariable String type){
        return adminService.countFoldersByType(type);
    }

    @GetMapping("/dossiers/count/total/{year}")
   public Collection<FormClass.DossierByUserAndYear> CountFoldersByYear(@PathVariable int year){
        return adminService.getTotalFoldersByYear(year);
   }

   @GetMapping("/user/{id}/folders/{type}/{year}")
   public ResponseEntity<List<DossierDTO>> FoldersByUserByTypeAndByYear(@PathVariable Long id,@PathVariable String type,@PathVariable int year){
        return adminService.getFoldersByClientByTypeAndByYear(id,type,year);
   }
}
