package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.EventRepository;
import com.teranil.nejtrans.dao.RoleRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.mapper.DossierConverter;
import com.teranil.nejtrans.mapper.UserConverter;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.Event;
import com.teranil.nejtrans.model.FormClass.FormClass;
import com.teranil.nejtrans.model.Role;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.model.dto.UserDTO;
import com.teranil.nejtrans.security.SecurityParams;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final DossierRepository dossierRepository;
    private final RoleRepository roleRepository;
    private final EventRepository eventRepository;
    private final DossierConverter dossierConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok().body(userConverter.entityToDto(userRepository.findAll()));
    }

    //Admin can get List of completed folders via employee username
    public ResponseEntity<List<DossierDTO>> getEmployeeCompletedFolders(String empUsername) {
        User user = userRepository.findByUsername(empUsername);
        if (Objects.isNull(user)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Dossier> dossierList = dossierRepository.findByEmployeeUsername(empUsername);
        List<Dossier> compList = new ArrayList<>();
        for (Dossier dossier : dossierList) {
            if (dossier.getAvailable() == 3)
                compList.add(dossier);
        }
        return ResponseEntity.ok().body(dossierConverter.entityToDto(compList));
    }

    public ResponseEntity<Integer> getEmployeeFoldersCountByType(String username,String type){
        List<Dossier> dossierList=dossierRepository.findByEmployeeUsernameAndTypeDossier(username,type);
        return ResponseEntity.ok().body(dossierList.size());
    }

    //Admin can get a list of completed folders by employee and folder type
    public ResponseEntity<List<DossierDTO>> getEmployeeCompletedFoldersByType(String empUsername, String type) {
        User user = userRepository.findByUsername(empUsername);
        if (Objects.isNull(user)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().body(dossierConverter.entityToDto(dossierRepository.findByEmployeeUsernameAndTypeDossier(empUsername, type)));

    }

    //Admin can get a list of employee folders by username
    public ResponseEntity<List<DossierDTO>> getEmployeeFolders(String empUsername) {
        User user = userRepository.findByUsername(empUsername);
        if (Objects.isNull(user)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().body(dossierConverter.entityToDto(dossierRepository.findByEmployeeUsername(empUsername)));
    }

    //admin can get a list of folders for each specific user by username
    public ResponseEntity<List<DossierDTO>> userFoldersByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().body(dossierConverter.entityToDto((List<Dossier>) user.getDossier()));
    }

    //Get folders list by user ID
    public ResponseEntity<Collection<DossierDTO>> getUserFolderList(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().body(dossierConverter.entityToDto(user.get().getDossier()));
    }

    public Collection<FormClass.DossierByUserAndYear> getUserFoldersListByYear(Long id,int year) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return null;
        }
      Collection<Dossier> collection=user.get().getDossier();
    return function(collection,year);
    }

    public Collection<FormClass.DossierByUserAndYear> getEmpFoldersListByYear(int year,String emp,int available){
        List<Dossier> dossiers=dossierRepository.findByEmployeeUsernameAndAvailable(emp,available);
        return function(dossiers,year);
    }


    public ResponseEntity<List<DossierDTO>> getfolders(Long id){
        return ResponseEntity.ok().body(dossierConverter.entityToDto(dossierRepository.findByUserId(id)));
    }

    //Get folders list by user ID and folder type
    public ResponseEntity<List<DossierDTO>> getFoldersListByTypeForUser(Long id, String type) {
        User user = userRepository.getById(id);
        return ResponseEntity.ok().body(dossierConverter.entityToDto(dossierRepository.findByTypeDossierAndUser(type, user)));
    }


    public void CreateUser(UserDTO userDTO,Long roleid){
        User user = userConverter.dtoToEntity(userDTO);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.getById(roleid)));
        userRepository.save(user);
    }


    public ResponseEntity<String> CreateEvent(FormClass.UserEventForm form){
        LocalDateTime dateS = LocalDateTime.parse(form.getDateStart());
        LocalDateTime dateE = LocalDateTime.parse(form.getDateEnd());
        if (dateS.isAfter(dateE) ||
                dateE.isEqual(dateS) ||
                dateE.isBefore(LocalDateTime.now()) ||
                dateS.isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Your date does not fit criteria");
        }
        Event event=new Event();
        event.setEventUser(userRepository.findByUsername(form.getUsername()));
        event.setDescription(form.getDescription());
        event.setTitle(form.getTitle());
        event.setStartDate(dateS);
        event.setEndDate(dateE);
        eventRepository.save(event);
        return ResponseEntity.ok().body("Created Succesffuly");
    }



    public Collection<FormClass.DossierByUserAndYear>function(Collection<Dossier> d ,int year){
        Collection<FormClass.DossierByUserAndYear> resultlist=new ArrayList<>();
        List<LocalDateTime> times=new ArrayList<>();

        for(Dossier dossier:d){
            if(year==dossier.getCreatedAt().getYear())
                times.add(dossier.getCreatedAt());
        }

        Map < YearMonth, List < LocalDateTime > > mapYearMonthToLdts = new TreeMap <>();

        for ( LocalDateTime ldt : times )
        {
            List<LocalDateTime> listOfLdts = mapYearMonthToLdts.computeIfAbsent(YearMonth.from(ldt), k -> new ArrayList<>());
            listOfLdts.add( ldt );
        }


        for ( YearMonth yearMonth : mapYearMonthToLdts.keySet() )
        {
            FormClass.DossierByUserAndYear result=new FormClass.DossierByUserAndYear();
            result.setMonth(yearMonth.getMonth().toString());
            result.setYear(yearMonth.getYear());
            result.setCount(mapYearMonthToLdts.get( yearMonth ).size());
            resultlist.add(result);
        }

        return resultlist;
    }
}
