package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.Util.HelperClass;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class RapportService {
    private final DossierRepository dossierRepository;
    private final UserRepository userRepository;
    public static List<String> months = Arrays.asList("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");



    public ResponseEntity<List<HelperClass.RapportHelper>> getEntreprise(int year){
        List<Dossier> dossiers=dossierRepository.findAll();
        List<HelperClass.RapportHelper> rapport=new ArrayList<>();

        for(String month : months){
            HelperClass.RapportHelper rapportmonth= new HelperClass.RapportHelper();
            rapportmonth.setMonth(month);
            for(Dossier dossier:dossiers) {
                if (dossier.getCreatedAt().getYear() == year) {
                    if (Objects.equals(dossier.getCreatedAt().getMonth().toString(), month)) {
                        if (Objects.equals(dossier.getTypeDossier(), "Import")) {

                            rapportmonth.setCountImport(rapportmonth.getCountImport() + 1);
                        } else if (Objects.equals(dossier.getTypeDossier(), "Export")) {

                            rapportmonth.setCountExport(rapportmonth.getCountExport() + 1);
                        }
                        rapportmonth.setCountTotal(rapportmonth.getCountImport()+ rapportmonth.getCountExport());
                    }

                }
            }
            rapport.add(rapportmonth);
        }


        return ResponseEntity.ok().body(rapport);
    }

    public ResponseEntity<List<HelperClass.RapportHelperEmployee>> getEmployee(int year){
        List<HelperClass.RapportHelperEmployee> rapport=new ArrayList<>();
        List<User> users=userRepository.findByRoles_Id(2L);
        int i=0;
        for(String month : months){
            HelperClass.RapportHelperEmployee rapportmonth= new HelperClass.RapportHelperEmployee();
            rapportmonth.setMonth(month);
            for(User user:users) {
                HelperClass.data Data=new HelperClass.data();
                for(Dossier dossier:dossierRepository.findByEmployeeUsername(user.getUsername())) {
                    CountYearMonthRapport(year, month, Data, dossier);
                }
                Data.setUsername(user.getUsername());
                rapportmonth.getData().add(Data);

            }
            rapport.add(rapportmonth);

        }


        return ResponseEntity.ok().body(rapport);
    }

    private void CountYearMonthRapport(int year, String month, HelperClass.data data, Dossier dossier) {
        if (Objects.equals(dossier.getCreatedAt().getMonth().toString(), month) && Objects.equals(dossier.getCreatedAt().getYear(),year)) {
            if (Objects.equals(dossier.getTypeDossier(), "Import")) {
                data.setCountImport(data.getCountImport() + 1);
            } else if (Objects.equals(dossier.getTypeDossier(), "Export")) {
                data.setCountExport(data.getCountExport() + 1);
            }
            data.setCountTotal(data.getCountExport()+ data.getCountImport());
        }
    }

    public ResponseEntity<List<HelperClass.RapportHelperEmployee>> getClient(int year){
        List<HelperClass.RapportHelperEmployee> rapport=new ArrayList<>();
        List<User> users=userRepository.findByRoles_Id(3L);
        for(String month : months){
            HelperClass.RapportHelperEmployee rapportmonth= new HelperClass.RapportHelperEmployee();
            rapportmonth.setMonth(month);
            for(User user:users) {
                HelperClass.data Data=new HelperClass.data();
                for(Dossier dossier:user.getDossier()) {
                    CountYearMonthRapport(year, month, Data, dossier);
                }
                Data.setUsername(user.getUsername());
                rapportmonth.getData().add(Data);

            }
            rapport.add(rapportmonth);

        }


        return ResponseEntity.ok().body(rapport);
    }



    public HelperClass.CounterClass getAverageFoldersMounth(int year,int year2 , int mounth , int mounth2) {

        HelperClass.CounterClass counterClass = new HelperClass.CounterClass();
        List<Dossier> dossiers=dossierRepository.findAll();
        for(Dossier x : dossiers) {
            if (mounth == 0 || mounth2 == 0){
                if (Objects.equals(x.getCreatedAt().getYear(),year) ){
                    counterClass.setThisYear(counterClass.getThisYear()+1);
                }
                if (Objects.equals(x.getCreatedAt().getYear(),year2)){
                    counterClass.setLastYear(counterClass.getLastYear()+1);
                }

            }
            else {
                if (Objects.equals(x.getCreatedAt().getMonth().getValue(),mounth) && Objects.equals(x.getCreatedAt().getYear(),year) ){
                    counterClass.setThisMounth(counterClass.getThisMounth()+1);
                }
                if (Objects.equals(x.getCreatedAt().getMonth().getValue(),mounth2) && Objects.equals(x.getCreatedAt().getYear(),year2)){
                    counterClass.setLastMounth(counterClass.getLastMounth()+1);
                }
            }

        }
        counterClass.setResultMounth(counterClass.getThisMounth()-counterClass.getLastMounth());
        counterClass.setResultyear(counterClass.getThisYear()-counterClass.getLastYear());
        return counterClass;
    }

    public HelperClass.CounterClass getAverageFoldersPerTypeMounth(String type) {
        HelperClass.CounterClass counterClass = new HelperClass.CounterClass();
        LocalDateTime lt = LocalDateTime.now();
        List<Dossier> dossiers=dossierRepository.findByTypeDossier(type);
        for(Dossier x : dossiers) {
            LocalDateTime date1 = x.getCreatedAt();
            if ( lt.getMonth().getValue() == date1.getMonth().getValue()){
                counterClass.setThisMounth(counterClass.getThisMounth()+1);
            }
            else if ((lt.getMonth().getValue()-1) == date1.getMonth().getValue()){
                counterClass.setLastMounth(counterClass.getLastMounth()+1);
            }
        }
        counterClass.setResultMounth(counterClass.getThisMounth()-counterClass.getLastMounth());
        return counterClass;
    }

    public HelperClass.CounterClassYear getAverageFoldersYear() {

        HelperClass.CounterClassYear counterClass = new HelperClass.CounterClassYear();
        LocalDateTime lt = LocalDateTime.now();
        List<Dossier> dossiers=dossierRepository.findAll();
        for(Dossier x : dossiers) {
            LocalDateTime date1 = x.getCreatedAt();
            if ( lt.getYear() == date1.getYear()){
                counterClass.setThisYear(counterClass.getThisYear()+1);
            }
            else if ((lt.getYear()-1) == date1.getYear()){
                counterClass.setLastYear(counterClass.getLastYear()+1);
            }
        }
        counterClass.setResult(counterClass.getThisYear()-counterClass.getLastYear());

        return counterClass;
    }
    public HelperClass.CounterClassYear getAverageFoldersPerTypeYear(String type) {
        HelperClass.CounterClassYear counterClass = new HelperClass.CounterClassYear();
        LocalDateTime lt = LocalDateTime.now();
        List<Dossier> dossiers=dossierRepository.findByTypeDossier(type);
        for(Dossier x : dossiers) {
            LocalDateTime date1 = x.getCreatedAt();
            if ( lt.getYear() == date1.getYear()){
                counterClass.setThisYear(counterClass.getThisYear()+1);
            }
            else if ((lt.getYear()-1) == date1.getMonth().getValue()){
                counterClass.setLastYear(counterClass.getLastYear()+1);
            }
        }
        counterClass.setResult(counterClass.getThisYear()-counterClass.getLastYear());
        return counterClass;
    }


//
     public ResponseEntity<HelperClass.rapportNejtransStat> getStatEntraprise(HelperClass.betweenToDate betweenToDate){
         HelperClass.rapportNejtransStat rapportNejtransStat = new HelperClass.rapportNejtransStat();
        if (betweenToDate.getStartAt().isAfter(betweenToDate.getEndAt())){
            return ResponseEntity.badRequest().body(rapportNejtransStat);
        }

         List<Dossier> dossiers=dossierRepository.findAll();
        for (Dossier x : dossiers) {
             if (x.getCreatedAt().isAfter(betweenToDate.getStartAt()) && x.getCreatedAt().isBefore(betweenToDate.getEndAt())){
                switch (x.getTypeDossier()){
                    case "Export": rapportNejtransStat.countExport++;
                    case "Import": rapportNejtransStat.countImport++;
                    break;
                }
                 switch (x.getAvailable()){
                     case 1: rapportNejtransStat.countEnAttente++;
                     case 2: rapportNejtransStat.countEnTraitement++;
                     case 3: rapportNejtransStat.countTerminer++;
                         break;
                 }
                 switch (x.getOperation()){
                     case "Travail rénumeré": rapportNejtransStat.countTr++;
                     case "Operation urgente": rapportNejtransStat.countOu++;
                     case "Operation normale": rapportNejtransStat.countOn++;
                         break;
                 }

                 rapportNejtransStat.countTotal ++;
             }
         }
    return ResponseEntity.ok().body(rapportNejtransStat);
     }
//

    public  ResponseEntity<List<HelperClass.ChartOperationsperYear>> getEntrepriseFoldersChartOperations(int year){
        List<Dossier> dossiers=dossierRepository.findAll();
        List<HelperClass.ChartOperationsperYear> chartOperationsperYears=new ArrayList<>();
        List<Dossier> result=new ArrayList<>();
        for(Dossier dossier:dossiers){
            if(dossier.getCreatedAt().getYear() == year){
                result.add(dossier);
            }
        }
        for(String month : months){
            HelperClass.ChartOperationsperYear chartperYear= new HelperClass.ChartOperationsperYear();
            chartperYear.setMonth(month);
            for(Dossier dossier:result) {
                if (Objects.equals(dossier.getCreatedAt().getMonth().toString(), month)) {
                    if (Objects.equals(dossier.getOperation(), "Travail rénumeré")) {

                        chartperYear.setTr(chartperYear.getTr() + 1);
                    }
                    else if (Objects.equals(dossier.getOperation(), "Operation urgente")) {
                        chartperYear.setOu(chartperYear.getOu() + 1);
                    }
                    else if (Objects.equals(dossier.getOperation(), "Operation normale")) {
                        chartperYear.setOn(chartperYear.getOn() + 1);
                    }
                }

            }
            chartOperationsperYears.add(chartperYear);
        }
        return ResponseEntity.ok().body(chartOperationsperYears);
    }

    public  ResponseEntity<List<HelperClass.ChartDispoperYear>> getEntrepriseFoldersChartDispo(int year){

        List<Dossier> dossiers=dossierRepository.findAll();
        List<HelperClass.ChartDispoperYear> chartDispoperYears=new ArrayList<>();
        List<Dossier> result=new ArrayList<>();

        for(Dossier dossier:dossiers){
            if(dossier.getCreatedAt().getYear() == year){
                result.add(dossier);
            }
        }
        for(String month : months){
            HelperClass.ChartDispoperYear chartperYear= new HelperClass.ChartDispoperYear();
            chartperYear.setMonth(month);
            for(Dossier dossier:result) {
                if (Objects.equals(dossier.getCreatedAt().getMonth().toString(), month)) {
                    if (dossier.getAvailable() == 1) {

                        chartperYear.setEnAttente(chartperYear.getEnAttente() + 1);
                    }
                    else if (dossier.getAvailable() == 2) {
                        chartperYear.setEnTraitement(chartperYear.getEnTraitement() + 1);
                    }
                    else if (dossier.getAvailable() == 3) {
                        chartperYear.setTerminer(chartperYear.getTerminer() + 1);
                    }
                }

            }
            chartDispoperYears.add(chartperYear);
        }
        return ResponseEntity.ok().body(chartDispoperYears);
    }


    public ResponseEntity<List<HelperClass.ByRoleChartperYearPie>> getRoleChartPie(Long role , HelperClass.betweenToDate betweenToDate){

        List<User> users = userRepository.findByRoles_Id(role);
        List<HelperClass.ByRoleChartperYearPie> byRoleChartperYearPieList = new ArrayList<>();
        List<Dossier> dossiers = dossierRepository.findAll();

        for (User user: users) {
            HelperClass.ByRoleChartperYearPie byRoleChart = new HelperClass.ByRoleChartperYearPie();
            byRoleChart.setUsername(user.getUsername());
           if (role == 2){

               for (Dossier x : dossiers) {
                   if (x.getEmployeeUsername().equals(user.getUsername()) && x.getCreatedAt().isAfter(betweenToDate.getStartAt()) && x.getCreatedAt().isBefore(betweenToDate.getEndAt())){
                       byRoleChart.setTotal(byRoleChart.getTotal()+1);
                   }
               }
           }
           else if (role == 3){
               for (Dossier x: user.getDossier()) {
                   if ( x.getCreatedAt().isAfter(betweenToDate.getStartAt()) && x.getCreatedAt().isBefore(betweenToDate.getEndAt())){
                       byRoleChart.setTotal(byRoleChart.getTotal()+1);
                   }
               }
           }
           byRoleChartperYearPieList.add(byRoleChart);
        }
        return ResponseEntity.ok().body(byRoleChartperYearPieList);
    }
}
