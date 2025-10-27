package in.ashar.authorization_server.service;

import in.ashar.authorization_server.entity.UserCredential;
import in.ashar.authorization_server.enums.UserCredentialRole;
import in.ashar.authorization_server.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserCredentialService {

    private final UserCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserCredential registerUser(String username, String rawPassword, boolean admin) {
        UserCredential user = UserCredential.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .roles(admin
                        ? Collections.singleton(UserCredentialRole.ADMIN)
                        : Collections.singleton(UserCredentialRole.USER))
                .build();

        return repository.save(user);
    }

    public UserCredential findByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }
}
