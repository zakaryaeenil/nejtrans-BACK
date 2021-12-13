package com.teranil.nejtrans.web;


import com.teranil.nejtrans.model.Util.HelperClass;
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
    @ApiOperation(value = "Used by Admin to get a count of all folders by type and year ")
    @GetMapping("/folders/{type}/{year}")
    public ResponseEntity<Integer> getFoldersCountByYear(@PathVariable String type,@PathVariable int year){
        return adminService.getFoldersListByYear(type,year);
    }



    @ApiOperation(value = "Used by Admin to get list of folders that a specific employee is working or worked on ")
    @GetMapping("/employee/{username}/folders")
    public ResponseEntity<List<DossierDTO>> getEmpFoldersNumber(@PathVariable String username) {
        return adminService.getEmployeeFolders(username);
    }


    @ApiOperation(value = "Used by Admin to get the count of folders that a specific employee is working or worked on by Folder type (Import or Export)")
    @GetMapping("/employee/{username}/folders/{type}")
    public ResponseEntity<Integer> getEmpFoldersNumberByType(@PathVariable String username,@PathVariable String type) {
        return adminService.getEmployeeFoldersCountByType(username,type);
    }

    @ApiOperation(value = "Used by Admin to get a the count of folders that a specific employee has worked on by type and by year ")
    @GetMapping("/employee/{username}/count/{type}/{year}")
    public ResponseEntity<Integer> getEmpFoldersNumberByTypeAndYear(@PathVariable String username,@PathVariable String type,@PathVariable int year) {
        return adminService.getEmployeeFoldersCountByTypeAndYear(username,type,year);
    }


    @ApiOperation(value = "Used by Admin to get a list of folders for a specific user  ")
    @GetMapping("/user/{id}/folders")
    public ResponseEntity<Collection<DossierDTO>> getUserFolders(@PathVariable Long id) {
        return adminService.getUserFolderList(id);
    }
    @ApiOperation(value = "Used by Admin to get a list of folders for a specific user by Year  ")
    @GetMapping("/user/{id}/folder/{year}")
    public ResponseEntity<Collection<DossierDTO>> getUserFoldersByYear(@PathVariable Long id,@PathVariable int year){
        return adminService.getUserFolderListByYear(id,year);
    }



        @ApiOperation(value = "Used by Admin to get count of folders by year and month for a specific user  ")
        @GetMapping("/user/{id}/folders/{year}")
    public Collection<HelperClass.DossierByUserAndYear> getUserFoldersByYearAndMonth(@PathVariable Long id, @PathVariable int year) {
            return adminService.getUserFoldersListByYear(id,year);
        }

    @ApiOperation(value = "Used by Admin to get count of folders by year and month and by availability for a specific employee   ")
    @GetMapping("/employee/{username}/folders/{available}/{year}")
        public Collection<HelperClass.DossierByUserAndYear> getEmpFoldersByAvailableAndYear(@PathVariable String username, @PathVariable int available, @PathVariable int year){
        return adminService.getEmpFoldersListByYear(year,username,available);
        }

    @ApiOperation(value = "Used by Admin to create another admin, a new client or a new employee (1=ADMIN) (2=EMPLOYEE) (3=CLIENT) ")
        @PostMapping("/user/create/role/{id}")
        public void CreateUser(@RequestBody UserDTO user, @PathVariable Long id){
         adminService.CreateUser(user,id);
}

    @ApiOperation(value = "Used by admin to create a new event for a specific user")
    @PostMapping("/event/create")
    public ResponseEntity<String> CreateEvent(@RequestBody HelperClass.UserEventForm form){
        return adminService.CreateEvent(form);
    }

    @ApiOperation(value = "Used by Admin to get a list of folders by type for a specific user ")
    @GetMapping("/user/{id}/dossiers/{type}")
    public ResponseEntity<List<DossierDTO>> getUserFolderByType(@PathVariable Long id, @PathVariable String type) {
        return adminService.getFoldersListByTypeForUser(id, type);
    }
    @ApiOperation(value = "Used by Admin to get count of all folders by type ")
    @GetMapping("/dossiers/count/{type}")
    public ResponseEntity<Integer> CountFolders(@PathVariable String type){
        return adminService.countFoldersByType(type);
    }

    @ApiOperation(value = "Used by Admin to get count of all folders by year and month")
    @GetMapping("/dossiers/count/total/{year}")
   public Collection<HelperClass.DossierByUserAndYear> CountFoldersByYear(@PathVariable int year){
        return adminService.getTotalFoldersByYear(year);
   }

    @ApiOperation(value = "Used by Admin to get list of all folders for a specific user by type and year ")
    @GetMapping("/user/{id}/folders/{type}/{year}")
   public ResponseEntity<List<DossierDTO>> FoldersByUserByTypeAndByYear(@PathVariable Long id,@PathVariable String type,@PathVariable int year){
        return adminService.getFoldersByClientByTypeAndByYear(id,type,year);
   }
}
