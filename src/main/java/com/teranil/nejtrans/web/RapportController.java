package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.Util.HelperClass;


import com.teranil.nejtrans.service.RapportService;
import io.swagger.annotations.Api;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/rapport")
@Api(value = "Admin operations", description = "Operations pertaining to Admins in Nejtrans Application")

public class RapportController {
    private final RapportService rapportService;

    @GetMapping("/all")
    public HelperClass.CounterClass getAverageFoldersMounth(){
        return rapportService.getAverageFoldersMounth();
    }

    @GetMapping("/{type}")
    public HelperClass.CounterClass getAverageFoldersPerTypeMounth(@PathVariable String type){
        return rapportService.getAverageFoldersPerTypeMounth(type);
    }

    @GetMapping("/year/all")
    public HelperClass.CounterClassYear getAverageFoldersYear(){
        return rapportService.getAverageFoldersYear();
    }

    @GetMapping("year/{type}")
    public HelperClass.CounterClassYear getAverageFoldersPerTypeYear(@PathVariable String type){
        return rapportService.getAverageFoldersPerTypeYear(type);
    }

    @GetMapping("/getmonths/{year}")
    public ResponseEntity<List<HelperClass.RapportHelper>> getRapportcount(@PathVariable int year){
        return rapportService.getEntreprise(year);
    }

    @GetMapping("/getmonthsAgents")
    public ResponseEntity<List<HelperClass.RapportHelperEmployee>> getRapportcountForEmployee(){
        return rapportService.getEmployee();
    }

    @GetMapping("/getmonthsClients")
    public ResponseEntity<List<HelperClass.RapportHelperEmployee>> getRapportcountForClients(){
        return rapportService.getClient();
    }




}
