package in.ashar.authorization_server.repository;

import in.ashar.authorization_server.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {

    Optional<UserCredential> findByUsername(String username);

}
