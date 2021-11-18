package com.teranil.nejtrans.service;

import com.teranil.nejtrans.dao.DocumentRepository;
import com.teranil.nejtrans.dao.DossierRepository;
import com.teranil.nejtrans.dao.RoleRepository;
import com.teranil.nejtrans.dao.UserRepository;
import com.teranil.nejtrans.model.Document;
import com.teranil.nejtrans.model.Dossier;
import com.teranil.nejtrans.model.Role;
import com.teranil.nejtrans.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class inittest implements CommandLineRunner {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DossierRepository dossierRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void initRole(){
        Role R = new Role();
        Role U = new Role();
        Role R3 = new Role();
        R.setName("ADMIN");
        U.setName("USER");
        R3.setName("EMPLOYEE");
        roleRepository.save(R);
        roleRepository.save(U);
        roleRepository.save(R3);
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
        u.getRoles().add(roleRepository.findByName("ADMIN"));
        u.getRoles().add(roleRepository.findByName("USER"));


        u2.setEmail("employeeEmail@gmail.com");
        u2.setUsername("user2");
        u2.setFirstName("zaki");
        u.setPassword(bCryptPasswordEncoder.encode("password"));
        u2.setLastName("hehe");
        u2.getRoles().add(roleRepository.findByName("USER"));
        u2.getRoles().add(roleRepository.findByName("EMPLOYEE"));


        u3.setEmail("User2Email@gmail.com");
        u3.setUsername("user3");
        u3.setFirstName("mehdi");
        u.setPassword(bCryptPasswordEncoder.encode("password"));
        u3.setLastName("ghnim");
        u3.getRoles().add(roleRepository.findByName("USER"));

        userRepository.save(u);
        userRepository.save(u2);
        userRepository.save(u3);

    }

    public void initDossier(){
        Dossier d=new Dossier();
        Dossier d2=new Dossier();
        d.setAvailable(true);
        d.setCompleted(true);
        d.setTypeDossier("Import");
        d.setReservation_username("user2");
        d.setUser(userRepository.findByUsername("user3"));

        d2.setAvailable(false);
        d2.setTypeDossier("Export");
        d2.setReservation_username("admin1");
        d2.setUser(userRepository.findByUsername("user2"));

        dossierRepository.save(d);
        dossierRepository.save(d2);

    }

    public void initDocument(){
    Document doc=new Document();
    Document doc2=new Document();
        doc.setName("Doc1");
        doc.setType_Document("CV");
        doc.setDoc_dossier(dossierRepository.getById(1L));


        doc2.setName("Doc2");
        doc2.setType_Document("LETTRE");
        doc2.setDoc_dossier(dossierRepository.getById(2L));

        documentRepository.save(doc);
        documentRepository.save(doc2);
    }

    @Override
    public void run(String... args) throws Exception {
        initRole();
        initUser();
        initDossier();
        initDocument();
    }
}
