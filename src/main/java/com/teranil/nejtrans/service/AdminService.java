package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.*;
import com.teranil.nejtrans.mapper.DossierConverter;
import com.teranil.nejtrans.mapper.ToDoConverter;
import com.teranil.nejtrans.mapper.UserConverter;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.Event;
import com.teranil.nejtrans.model.Util.HelperClass;
import com.teranil.nejtrans.model.User;
import com.teranil.nejtrans.model.dto.DossierDTO;
import com.teranil.nejtrans.model.dto.ToDoDTO;
import com.teranil.nejtrans.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

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
    private final ToDoRepository toDoRepository;
    private final ToDoConverter toDoConverter;
    private final MailSenderService mailSenderService;

    public static List<String> months = Arrays.asList("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");

    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok().body(userConverter.entityToDto(userRepository.findAll()));
    }



    public ResponseEntity<List<ToDoDTO>> getAllTodos(){
        return ResponseEntity.ok().body(toDoConverter.entityToDto(toDoRepository.findAll()));
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


    public ResponseEntity<Integer> getEmployeeFoldersCountByTypeAndYear(String username,String type,int year){
        List<Dossier> dossierList=dossierRepository.findByEmployeeUsernameAndTypeDossier(username,type);
        List<Dossier> result=new ArrayList<>();
        for(Dossier dossier:dossierList){
            if(dossier.getCreatedAt().getYear()==year){
                result.add(dossier);
            }
        }
        return ResponseEntity.ok().body(result.size());
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


    public ResponseEntity<Collection<DossierDTO>> getUserFolderListByYear(Long id,int year) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Collection<Dossier> dossierList=user.get().getDossier();
        Collection<Dossier> result = new ArrayList<>();
        for(Dossier dossier:dossierList){
            if(dossier.getCreatedAt().getYear()==year)
                result.add(dossier);
        }


        return ResponseEntity.ok().body(dossierConverter.entityToDto(result));
    }

    public ResponseEntity<Integer> getFoldersListByYear(String type,int year){
        Collection<Dossier> dossiers=dossierRepository.findByTypeDossier(type);
        Collection <Dossier> result=new ArrayList<>();
        for(Dossier dossier:dossiers){
            if(dossier.getCreatedAt().getYear()==year){
                result.add(dossier);
            }
        }
        return ResponseEntity.ok().body(result.size());
    }


    public Collection<HelperClass.DossierByUserAndYear> getUserFoldersListByYear(Long id, int year) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return null;
        }
      Collection<Dossier> collection=user.get().getDossier();
    return function(collection,year);
    }

    public Collection<HelperClass.DossierByUserAndYear> getEmpFoldersListByYear(int year, String emp, int available){
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

    public ResponseEntity<List<DossierDTO>> getFoldersByClientByTypeAndByYear(Long id,String type,int year){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Dossier> dossierList;
        List<Dossier> result=new ArrayList<>();
        switch(type){
            case "Import":
            case "Export":
                dossierList=dossierRepository.findByTypeDossierAndUser(type,user.get());
                break;
            case "All":
                dossierList= (List<Dossier>) user.get().getDossier();
                break;
            default :
                return ResponseEntity.badRequest().body(null);

        }
        for(Dossier dossier:dossierList){
            if(dossier.getCreatedAt().getYear()==year){
                result.add(dossier);
            }
        }
        return ResponseEntity.ok().body(dossierConverter.entityToDto(result));
    }

    public Collection<HelperClass.DossierByUserAndYear> getTotalFoldersByYear(int year){
        List<Dossier> dossiers=dossierRepository.findAll();
        return function(dossiers,year);
    }

    public ResponseEntity<String> CreateEvent(HelperClass.UserEventForm form){
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


        public ResponseEntity<Integer> countFoldersByType(String type){
        int count ;
            System.out.println(type);
            switch(type){
                case "EmpTotal":
                    List<User> EmpList= userRepository.findByRoles_Id(2L);
                    count=EmpList.size();
                    break;
                case "ClientTotal":
                    List<User> ClientsList=userRepository.findByRoles_Id(3L);
                   count=ClientsList.size();
                    break;
                case "FolTotal":
                    List<Dossier> dossierList=dossierRepository.findAll();
                    count=dossierList.size();
                    break;
                case "ImportTotal":
                    List<Dossier> dossier=dossierRepository.findByTypeDossier("Import");
                    count=dossier.size();
                    break;
                case "ExportTotal":
                    List<Dossier> dossiers=dossierRepository.findByTypeDossier("Export");
                    count=dossiers.size();
                    break;
                default:
                   return ResponseEntity.badRequest().body(null);
            }
        return ResponseEntity.ok().body(count);
        }


    public Collection<HelperClass.DossierByUserAndYear> function(Collection<Dossier> d , int year){
        Collection<HelperClass.DossierByUserAndYear> resultlist=new ArrayList<>();
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
            HelperClass.DossierByUserAndYear result=new HelperClass.DossierByUserAndYear();
            result.setMonth(yearMonth.getMonth().toString());
            result.setYear(yearMonth.getYear());
            result.setCount(mapYearMonthToLdts.get( yearMonth ).size());
            resultlist.add(result);
        }

        return resultlist;
    }


    public  ResponseEntity<List<HelperClass.ChartperYear>> getfolderschart(int year){
        List<Dossier> dossiers=dossierRepository.findAll();
        List<HelperClass.ChartperYear> chartperYearList=new ArrayList<>();
        List<Dossier> result=new ArrayList<>();
        for(Dossier dossier:dossiers){
            if(dossier.getCreatedAt().getYear() == year){
                result.add(dossier);
            }
        }
        for(String month : months){
            HelperClass.ChartperYear chartperYear= new HelperClass.ChartperYear();
            chartperYear.setMonth(month);
                for(Dossier dossier:result) {
                    if (Objects.equals(dossier.getCreatedAt().getMonth().toString(), month)) {
                        if (Objects.equals(dossier.getTypeDossier(), "Import")) {

                            chartperYear.setImpo(chartperYear.getImpo() + 1);
                        }
                        else if (Objects.equals(dossier.getTypeDossier(), "Export")) {
                            chartperYear.setExpo(chartperYear.getExpo() + 1);
                        }
                    }
                    chartperYear.setTotal(chartperYear.getImpo()+chartperYear.getExpo());
                }
            chartperYearList.add(chartperYear);
            }
        return ResponseEntity.ok().body(chartperYearList);
    }



    public  ResponseEntity<List<HelperClass.ChartperYear>> getfolderschartByUser(Long id  , int year){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Dossier> dossiers=dossierRepository.findAll();
        List<HelperClass.ChartperYear> chartperYearList=new ArrayList<>();
        List<Dossier> result=new ArrayList<>();
        for(Dossier dossier:dossiers){
            if(Objects.equals(dossier.getCreatedAt().getYear(), year) && Objects.equals(dossier.getUser().getId(), user.get().getId())){
                result.add(dossier);
            }
        }
        for(String month : months){
            HelperClass.ChartperYear chartperYear= new HelperClass.ChartperYear();
            chartperYear.setMonth(month);
            for(Dossier dossier:result) {
                if (Objects.equals(dossier.getCreatedAt().getMonth().toString(), month)) {
                    if (Objects.equals(dossier.getTypeDossier(), "Import")) {

                        chartperYear.setImpo(chartperYear.getImpo() + 1);
                    }
                    else if (Objects.equals(dossier.getTypeDossier(), "Export")) {
                        chartperYear.setExpo(chartperYear.getExpo() + 1);
                    }
                }
                chartperYear.setTotal(chartperYear.getImpo()+chartperYear.getExpo());
            }
            chartperYearList.add(chartperYear);
        }
        return ResponseEntity.ok().body(chartperYearList);
    }

    public  ResponseEntity<List<HelperClass.ChartperYear>> getfolderschartByEmployee(String username , int year){
        List<Dossier> dossiers=dossierRepository.findAll();
        List<HelperClass.ChartperYear> chartperYearList=new ArrayList<>();
        List<Dossier> result=new ArrayList<>();
        for(Dossier dossier:dossiers){
            if(Objects.equals(dossier.getCreatedAt().getYear(), year) && Objects.equals(dossier.getEmployeeUsername(),username)  && Objects.equals(dossier.getAvailable(),HelperClass.Terminer)){
                result.add(dossier);
            }
        }
        for(String month : months){
            HelperClass.ChartperYear chartperYear= new HelperClass.ChartperYear();
            chartperYear.setMonth(month);
            for(Dossier dossier:result) {
                if (Objects.equals(dossier.getCreatedAt().getMonth().toString(), month)) {
                    if (Objects.equals(dossier.getTypeDossier(), "Import")) {

                        chartperYear.setImpo(chartperYear.getImpo() + 1);
                    }
                    else if (Objects.equals(dossier.getTypeDossier(), "Export")) {
                        chartperYear.setExpo(chartperYear.getExpo() + 1);
                    }
                }
                chartperYear.setTotal(chartperYear.getImpo()+chartperYear.getExpo());
            }
            chartperYearList.add(chartperYear);
        }
        return ResponseEntity.ok().body(chartperYearList);
    }
    public ResponseEntity<User> getUserbytoken() {
        String auth = SecurityContextHolder.getContext().getAuthentication().getName();
      User user =  userRepository.findByUsername(auth);
      return ResponseEntity.ok(user);

    }
}
