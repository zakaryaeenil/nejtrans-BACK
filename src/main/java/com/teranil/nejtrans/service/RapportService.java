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
        int i=0;
        for(String month : months){
            HelperClass.RapportHelper rapportmonth= new HelperClass.RapportHelper();
            rapportmonth.setMonth(month);
            for(Dossier dossier:dossiers) {
                if (dossier.getCreatedAt().getYear() == year) {
                    if (Objects.equals(dossier.getCreatedAt().getMonth().toString(), month)) {
                        if (Objects.equals(dossier.getTypeDossier(), "Import")) {
                            i++;
                            rapportmonth.setCountImport(rapportmonth.getCountImport() + 1);
                        } else if (Objects.equals(dossier.getTypeDossier(), "Export")) {
                            i++;
                            rapportmonth.setCountExport(rapportmonth.getCountExport() + 1);
                        }
                        rapportmonth.setCountTotal(i);
                    }

                }
            }
            rapport.add(rapportmonth);
        }


        return ResponseEntity.ok().body(rapport);
    }

    public ResponseEntity<List<HelperClass.RapportHelperEmployee>> getEmployee(){
        List<HelperClass.RapportHelperEmployee> rapport=new ArrayList<>();
        List<User> users=userRepository.findByRoles_Id(2L);
        int i=0;
        for(String month : months){
            HelperClass.RapportHelperEmployee rapportmonth= new HelperClass.RapportHelperEmployee();
            rapportmonth.setMonth(month);
            for(User user:users) {
                HelperClass.data Data=new HelperClass.data();

                for(Dossier dossier:dossierRepository.findByEmployeeUsername(user.getUsername())) {
                    if (Objects.equals(dossier.getCreatedAt().getMonth().toString(), month)) {
                        if (Objects.equals(dossier.getTypeDossier(), "Import")) {
                            i++;
                            Data.setCountImport(Data.getCountImport() + 1);
                        } else if (Objects.equals(dossier.getTypeDossier(), "Export")) {
                            i++;
                            Data.setCountExport(Data.getCountExport() + 1);
                        }
                        Data.setCountTotal(i);
                    }
                }
                Data.setUsername(user.getUsername());
                rapportmonth.getData().add(Data);

            }
            rapport.add(rapportmonth);

        }


        return ResponseEntity.ok().body(rapport);
    }

    public ResponseEntity<List<HelperClass.RapportHelperEmployee>> getClient(){
        List<HelperClass.RapportHelperEmployee> rapport=new ArrayList<>();
        List<User> users=userRepository.findByRoles_Id(3L);
        int i=0;
        for(String month : months){
            HelperClass.RapportHelperEmployee rapportmonth= new HelperClass.RapportHelperEmployee();
            rapportmonth.setMonth(month);
            for(User user:users) {
                HelperClass.data Data=new HelperClass.data();
                for(Dossier dossier:user.getDossier()) {
                    if (Objects.equals(dossier.getCreatedAt().getMonth().toString(), month)) {
                        if (Objects.equals(dossier.getTypeDossier(), "Import")) {
                            i++;
                            Data.setCountImport(Data.getCountImport() + 1);
                        } else if (Objects.equals(dossier.getTypeDossier(), "Export")) {
                            i++;
                            Data.setCountExport(Data.getCountExport() + 1);
                        }
                        Data.setCountTotal(i);
                    }
                }
                Data.setUsername(user.getUsername());
                rapportmonth.getData().add(Data);

            }
            rapport.add(rapportmonth);

        }


        return ResponseEntity.ok().body(rapport);
    }



    public HelperClass.CounterClass getAverageFoldersMounth() {

        HelperClass.CounterClass counterClass = new HelperClass.CounterClass();
        LocalDateTime lt = LocalDateTime.now();
        List<Dossier> dossiers=dossierRepository.findAll();
        for(Dossier x : dossiers) {
            LocalDateTime date1 = x.getCreatedAt();
            if ( lt.getMonth().getValue() == date1.getMonth().getValue()){
               counterClass.setThisMounth(counterClass.getThisMounth()+1);
            }
            else if ((lt.getMonth().getValue()-1) == date1.getMonth().getValue()){
                counterClass.setLastMounth(counterClass.getLastMounth()+1);
           }
        }
        counterClass.setResult(counterClass.getThisMounth()-counterClass.getLastMounth());

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
        counterClass.setResult(counterClass.getThisMounth()-counterClass.getLastMounth());
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





}
