package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.*;
import com.teranil.nejtrans.model.*;

import lombok.AllArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

import static com.teranil.nejtrans.model.Util.HelperClass.*;


@Service
@Transactional
@AllArgsConstructor
public class inittest implements CommandLineRunner {

    private final DocumentRepository documentRepository;

    private final DossierRepository dossierRepository;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final ToDoRepository toDoRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    public void initRole() {
        Role R = new Role();
        Role U = new Role();
        Role R3 = new Role();
        R.setName("ADMIN");
        R3.setName("EMPLOYEE");
        U.setName("USER");
        roleRepository.save(R);
        roleRepository.save(R3);
        roleRepository.save(U);

    }

    public void initUser() {
        User u = new User();
        User u2 = new User();
        User u3 = new User();

        u.setEmail("Admin1Email@gmail.com");
        u.setUsername("admin1");
        u.setFirstName("Jhonny");
        u.setPassword(bCryptPasswordEncoder.encode("password"));
        u.setLastName("Depp");
        u.setTelephone("0606060606");
        u.getRoles().add(roleRepository.findByName("ADMIN"));


        u2.setEmail("employeeEmail@gmail.com");
        u2.setUsername("employee1");
        u2.setFirstName("zaki");
        u2.setPassword(bCryptPasswordEncoder.encode("password"));
        u2.setLastName("hehe");
        u2.setTelephone("0606060606");
        u2.getRoles().add(roleRepository.findByName("EMPLOYEE"));

        User u4=new User();

        u4.setEmail("employssseeEmail@gmail.com");
        u4.setUsername("employee2");
        u4.setFirstName("zaki");
        u4.setPassword(bCryptPasswordEncoder.encode("password"));
        u4.setLastName("hehe");
        u4.setTelephone("0606060606");
        u4.getRoles().add(roleRepository.findByName("EMPLOYEE"));

        u3.setEmail("User2Email@gmail.com");
        u3.setUsername("user3");
        u3.setFirstName("mehdi");
        u3.setPassword(bCryptPasswordEncoder.encode("password"));
        u3.setLastName("aghnim");
        u3.setTelephone("0606060606");
        u3.getRoles().add(roleRepository.findByName("USER"));

        User u5 = new User();

        u5.setEmail("User4Email@gmail.com");
        u5.setUsername("user4");
        u5.setFirstName("Alami");
        u5.setPassword(bCryptPasswordEncoder.encode("password"));
        u5.setLastName("Mohamed");
        u5.setTelephone("0606060606");
        u5.getRoles().add(roleRepository.findByName("USER"));

        userRepository.save(u);
        userRepository.save(u2);
        userRepository.save(u3);
        userRepository.save(u4);
        userRepository.save(u5);
    }


    public void initToDo(){
        ToDo t=new ToDo();
        t.setDescription("Test To do");
        t.setType("Done");
        t.setUser(userRepository.getById(1L));
        t.setTitle("Done");

        ToDo t4=new ToDo();
        t4.setDescription("Test To do");
        t4.setType("Normal");
        t4.setUser(userRepository.getById(1L));
        t4.setTitle("Normal");

        ToDo t2=new ToDo();
        t2.setDescription("Test To do");
        t2.setType("Trash");
        t2.setUser(userRepository.getById(1L));
        t2.setTitle("Trash");

        ToDo t3=new ToDo();
        t3.setDescription("Test ihahahTo do");
        t3.setType("Important");
        t3.setUser(userRepository.getById(1L));
        t3.setTitle("Important");

        toDoRepository.save(t);
        toDoRepository.save(t2);
        toDoRepository.save(t3);
        toDoRepository.save(t4);



    }
    public void initDossier() {
        Dossier d = new Dossier();
        Dossier d2 = new Dossier();
        Dossier d3 = new Dossier();

        d.setAvailable(EnTraitement);
        d.setTypeDossier("Import");
        d.setEmployeeUsername("employee1");
        User user2 =userRepository.findByUsername(d.getEmployeeUsername());
        user2.setCountReservations(user2.getCountReservations()+1);
        d.setUser(userRepository.getById(3L));
        d.setOperation("Operation urgente");
        d.getUser().setCountDossiers(d.getUser().getCountDossiers()+1);


        d2.setAvailable(Terminer);
        d2.setTypeDossier("Export");
        d2.setEmployeeUsername("employee1");
        User user =userRepository.findByUsername(d2.getEmployeeUsername());
        user.setCountReservations(user.getCountReservations()+1);
        d2.setUser(userRepository.getById(3L));
        d2.setOperation("Operation normale");

        d2.getUser().setCountDossiers(d2.getUser().getCountDossiers()+1);



        d3.setAvailable(EnAttente);
        d3.setTypeDossier("Export");
        d3.setUser(userRepository.getById(3L));
        d3.setOperation("Travail rénumeré");
        d3.getUser().setCountDossiers(d3.getUser().getCountDossiers()+1);


        dossierRepository.save(d);
        dossierRepository.save(d2);
        dossierRepository.save(d3);

    }

    public void initDocument() throws IOException {
        Document doc = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();


        doc.setName("Doc1");
        doc.setTypeDocument("CV");
        doc.setDossier(dossierRepository.getById(1L));
        doc.getDossier().setNb_documents(doc.getDossier().getNb_documents() + 1);

        doc2.setName("Doc2");
        doc2.setTypeDocument("Lettre motivation");
        doc2.setDossier(dossierRepository.getById(2L));
        doc2.getDossier().setNb_documents(doc2.getDossier().getNb_documents() + 1);

        doc3.setName("Doc3");
        doc3.setTypeDocument("Acte naissance");
        doc3.setDossier(dossierRepository.getById(1L));
        doc3.getDossier().setNb_documents(doc3.getDossier().getNb_documents() + 1);


        documentRepository.save(doc);
        documentRepository.save(doc2);
        documentRepository.save(doc3);

    }

    public void initEvents(){

        String DateStart = "2021-12-29T08:00";
        String DateEnd = "2021-12-29T10:00";
        LocalDateTime dateS = LocalDateTime.parse(DateStart);
        LocalDateTime dateE = LocalDateTime.parse(DateEnd);
        Event b = new Event();
        Event b2 = new Event();
        Event b3 = new Event();
        b.setDescription("Event for testing purposes");
        b.setTitle("Event1");
        b.setEventUser(userRepository.findByUsername("user3"));
        b.setStartDate(dateS);
        b.setEndDate(dateE);
        eventRepository.save(b);

    }

    @Override
    public void run(String... args) throws Exception {
        initRole();
        initUser();
        initEvents();
        initDossier();
        initDocument();
        initToDo();

    }
}
