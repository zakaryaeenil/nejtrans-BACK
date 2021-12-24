package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.Util.HelperClass;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class RapportService {
    private final DossierRepository dossierRepository;

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
