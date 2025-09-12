package in.ashar.spring_security.service;

import in.ashar.spring_security.entity.Credential;
import in.ashar.spring_security.entity.Roles;
import in.ashar.spring_security.repository.CredentialRepository;
import in.ashar.spring_security.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class CustomInitializer implements CommandLineRunner {

    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if(!credentialRepository.existsByUsername("ashar")) {

            Roles role = new Roles("ADMIN");
            Credential credential = new Credential();
            credential.setUsername("ashar");
            credential.setPassword(passwordEncoder.encode("root"));
            credential.setRoles(new HashSet<>(List.of(role)));

            rolesRepository.save(role);
            credentialRepository.save(credential);
        }
        System.out.println("admin created..");
    }
}
