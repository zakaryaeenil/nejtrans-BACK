package com.teranil.nejtrans.web;

import com.teranil.nejtrans.model.Util.HelperClass;


import com.teranil.nejtrans.service.RapportService;
import io.swagger.annotations.Api;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/rapport")
@Api(value = "Admin operations", description = "Operations pertaining to Admins in Nejtrans Application")

public class RapportController {
    private final RapportService rapportService;

    @GetMapping("/all/{year}/{yeartocompare}/{mounth}/{mounthtocompare}")
    public HelperClass.CounterClass getAverageFoldersMounth(@PathVariable int year,@PathVariable int yeartocompare,@PathVariable int mounth,@PathVariable int mounthtocompare){
        return rapportService.getAverageFoldersMounth(year,yeartocompare,mounth,mounthtocompare);
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

    @GetMapping("/getmonthsAgents/{year}")
    public ResponseEntity<List<HelperClass.RapportHelperEmployee>> getRapportcountForEmployee(@PathVariable int year){
        return rapportService.getEmployee(year);
    }

    @GetMapping("/getmonthsClients/{year}")
    public ResponseEntity<List<HelperClass.RapportHelperEmployee>> getRapportcountForClients(@PathVariable int year){
        return rapportService.getClient(year);
    }
//

    @PutMapping("/all/stat/entreprise")
    public ResponseEntity<HelperClass.rapportNejtransStat> getStatEntraprise(@RequestBody HelperClass.betweenToDate betweenToDate){
        return rapportService.getStatEntraprise(betweenToDate);
    }
//

    @GetMapping("entreprise/charts/{year}/operations")
    public ResponseEntity<List<HelperClass.ChartOperationsperYear>> getEntrepriseFoldersChartOperations(@PathVariable int year){
        return rapportService.getEntrepriseFoldersChartOperations(year);
    }


    @GetMapping("entreprise/charts/{year}/dispo")
    public ResponseEntity<List<HelperClass.ChartDispoperYear>> getEntrepriseFoldersChartDispo(@PathVariable int year){
        return rapportService.getEntrepriseFoldersChartDispo(year);
    }

    @PutMapping("/agent/stat/charts/pie/{roleId}")
    public ResponseEntity<List<HelperClass.ByRoleChartperYearPie>> getRoleChartPie(@PathVariable Long roleId ,@RequestBody HelperClass.betweenToDate betweenToDate){
        return rapportService.getRoleChartPie(roleId, betweenToDate);
    }
}
