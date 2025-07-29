package com.ashar.profileManager.repository;

import com.ashar.profileManager.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Integer> {


    public List<Profile> findAllByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("delete from Profile p where p.name= :Pname")
    void deleteByName(@Param("Pname") String name);
}
