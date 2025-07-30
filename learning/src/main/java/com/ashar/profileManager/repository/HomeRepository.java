package com.ashar.profileManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface HomeRepository extends JpaRepository<Home, Integer> {

    // READ
    Optional<Home> findByName(String name);

    List<Home> findAllByName(String name);

    // UPDATE (custom implementations go in service or @Query if needed)

    // DELETE
    void deleteByName(String name);

    // EXISTS
    boolean existsByName(String name);

    // COUNT
    long countByName(String name);
}