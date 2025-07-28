package com.ashar.profileManager.controller;

import com.ashar.profileManager.entity.Profile;
import com.ashar.profileManager.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/profile-manager")
public class ProfileController {

    private final ProfileService profileService;

    //constructor Injection
    ProfileController(ProfileService profileService){
        this.profileService = profileService;
    }

    @PostMapping("/profile")
    public ResponseEntity<Profile> addData(@RequestBody Profile profile){
        log.info("creating profile");
        return profileService.addProfile(profile);
    }

    @GetMapping("/profile")
    public ResponseEntity<List<Profile>> getAllProfiles(){
        log.info("getting all profiles");
        return profileService.getAllProfiles();
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable int id){
        log.info("getting profile by id : {}",id);
        return profileService.getProfileById(id);
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<Profile> updateProfileById(@PathVariable int id, @RequestBody Profile profile){
        return profileService.updateProfileById(id,profile);
    }

    @DeleteMapping("/profile/{id}")
    public ResponseEntity<Profile> deleteProfileById(@PathVariable("id") int id){
        return profileService.deleteProfileById(id);
    }
}
