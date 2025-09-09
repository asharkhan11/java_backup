package in.ashar.spring_security.repository;

import in.ashar.spring_security.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {

    Optional<Roles> findByRole(String name);
}
