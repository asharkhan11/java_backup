package in.ashar.second_level_cache.repository;

import in.ashar.second_level_cache.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // READ
    Optional<Student> findByName(String name);

    List<Student> findAllByName(String name);

    // UPDATE (custom implementations go in service or @Query if needed)

    // DELETE
    void deleteByName(String name);

    // EXISTS
    boolean existsByName(String name);

    // COUNT
    long countByName(String name);
}