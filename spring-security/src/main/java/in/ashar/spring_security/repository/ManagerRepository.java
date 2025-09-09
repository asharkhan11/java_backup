package in.ashar.spring_security.repository;

import in.ashar.spring_security.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

//    // READ
//    Optional<Manager> findByName(String name);
//
//    List<Manager> findAllByName(String name);
//
//    // UPDATE (custom implementations go in service or @Query if needed)
//
//    // DELETE
//    void deleteByName(String name);
//
//    // EXISTS
//    boolean existsByName(String name);
//
//    // COUNT
//    long countByName(String name);
}