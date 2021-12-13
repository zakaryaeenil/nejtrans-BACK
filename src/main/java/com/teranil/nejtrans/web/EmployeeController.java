package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/employee")
@Api(value = "Employee operations", description = "Operations pertaining to clients in Nejtrans Application")
public class EmployeeController {

    private final EmployeeService employeeService;

    @ApiOperation(value = "Used by Logged in employee to get reserved folders")
    @GetMapping("/myfolders/{type}")
    public ResponseEntity<List<DossierDTO>> getEmployeeFolders(@PathVariable String type) {
        return employeeService.getEmployeeFolders(type);
    }

    @ApiOperation(value = "Used by Logged in employee to get reserved folders")
    @GetMapping("/myfolders/count/{type}")
    public ResponseEntity<Integer> getEmployeeFoldersCount(@PathVariable String type) {
        return employeeService.getEmployeeFoldersCount(type);
    }


    @ApiOperation(value = "Used by Logged in employee to reserve a Folder")
    @PatchMapping("/bookfoolder/{id}")
    public ResponseEntity<String> bookFolder(@PathVariable Long id) {
        return employeeService.bookFolder(id);
    }

    @ApiOperation(value = "Used by Logged in employee to get reserved folders")
    @GetMapping("/freefolders")
    public ResponseEntity<List<DossierDTO>> getFreeFolders() {
        return employeeService.getNonReservedFolders();
    }
}
