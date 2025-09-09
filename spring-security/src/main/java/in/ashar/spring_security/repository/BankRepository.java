package in.ashar.spring_security.repository;

import in.ashar.spring_security.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

//    // READ
//    Optional<Bank> findByName(String name);
//
//    List<Bank> findAllByName(String name);
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