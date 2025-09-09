package in.ashar.spring_security.service;

import in.ashar.spring_security.entity.Users;
import in.ashar.spring_security.repository.RolesRepository;
import in.ashar.spring_security.repository.UserDetailsRepository;
import in.ashar.spring_security.utility.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomInitializer {
    @Autowired
    private RolesRepository rolesRepository;

    @Bean
    public CommandLineRunner initializeUser(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder){

        return args -> {

            List<in.ashar.spring_security.entity.Roles> list = Arrays.stream(Roles.values()).map(r -> new in.ashar.spring_security.entity.Roles(r.name())).toList();
            rolesRepository.saveAll(list);

            if(userDetailsRepository.findByUsername("ashar").isEmpty()){
                Users user = new Users();
                user.setUsername("ashar");
                String encoded = passwordEncoder.encode("root");
                user.setPassword(encoded);

                in.ashar.spring_security.entity.Roles role = rolesRepository.findByRole(Roles.ADMIN.name()).orElseThrow(RuntimeException::new);
                user.getRoles().add(role);
                user.setRoles(user.getRoles());

                userDetailsRepository.save(user);
                System.out.println("admin created...");
            }

        };


    }

}
