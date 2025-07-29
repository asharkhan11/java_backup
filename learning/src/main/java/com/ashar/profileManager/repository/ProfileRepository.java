package com.ashar.profileManager.repository;

import com.ashar.profileManager.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Integer> {


//    public List<Profile> findAllByName(@Param("name") String name);
//
//    @Transactional
//    @Modifying
//    @Query("delete from Profile p where p.name= :Pname")
//    void deleteByName(@Param("Pname") String name);

    Optional<Profile> findByName(String name);


//    Optional<Profile> findByDateOfBirth(Date date);

}
