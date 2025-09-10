package in.ashar.spring_security.service;

import in.ashar.spring_security.entity.Users;
import in.ashar.spring_security.repository.RolesRepository;
import in.ashar.spring_security.repository.UsersRepository;
import in.ashar.spring_security.utility.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CustomInitializer {
    @Autowired
    private RolesRepository rolesRepository;

    @Bean
    public CommandLineRunner initializeUser(UsersRepository usersRepository, PasswordEncoder passwordEncoder){

        return args -> {

            Roles[] roles = Roles.values();
            List<in.ashar.spring_security.entity.Roles> rolesList = new ArrayList<>();

            for (Roles role : roles) {
                if(!rolesRepository.existsByRole(role.name())){
                    rolesList.add(new in.ashar.spring_security.entity.Roles(role.name()));
                }
            }

            rolesRepository.saveAll(rolesList);
            System.out.println("roles created : "+rolesList);
        };


    }

}
