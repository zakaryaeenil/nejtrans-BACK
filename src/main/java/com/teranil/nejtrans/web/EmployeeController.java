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
    @GetMapping("/myfolders")
    public ResponseEntity<List<DossierDTO>> getEmployeeFolders() {
        return employeeService.getEmployeeFolders();
    }

    @ApiOperation(value = "Used by Logged in employee to reserve a Folder")
    @PatchMapping("/bookfoolder/{id}")
    public ResponseEntity<String> bookFolder(@PathVariable Long id) {
        return employeeService.bookFolder(id);
    }

}
