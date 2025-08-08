package com.ashar.profileManager.service;

import com.ashar.profileManager.dto.ProfileDto;
import com.ashar.profileManager.entity.Profile;
import com.ashar.profileManager.entity.User;
import com.ashar.profileManager.exception.ResourceNotFoundException;
import com.ashar.profileManager.repository.ProfileRepository;
import com.ashar.profileManager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<Profile> addProfile(ProfileDto profileDto) {
        log.info("inside ProfileService, adding Profile");
        Optional<User> userExists = userRepository.findById(profileDto.getUserId());
        if (userExists.isEmpty())
            throw new ResourceNotFoundException("User not found with id : " + profileDto.getUserId());

        Profile profile = new Profile();
        profile.setUsername(profileDto.getUsername());
        profile.setPassword(profileDto.getPassword());
        profile.setProfilePicUrl(profileDto.getProfilePicUrl());
        profile.setBio(profileDto.getBio());
        profile.setUser(userExists.get());

        return new ResponseEntity<>(profileRepository.save(profile), HttpStatus.CREATED);

    }

    public ResponseEntity<List<Profile>> addAllProfiles(List<ProfileDto> profileDtos) {
        List<Profile> list = new ArrayList<>();
        log.info("inside ProfileService, adding All Profiles");

        for (ProfileDto profileDto : profileDtos) {
            Optional<User> userExists = userRepository.findById(profileDto.getUserId());
            if (userExists.isPresent()) {
                Profile profile = new Profile();
                profile.setUsername(profileDto.getUsername());
                profile.setPassword(profileDto.getPassword());
                profile.setProfilePicUrl(profileDto.getProfilePicUrl());
                profile.setBio(profileDto.getBio());
                profile.setUser(userExists.get());
                list.add(profile);
            }
        }
        return new ResponseEntity<>(profileRepository.saveAll(list), HttpStatus.CREATED);

    }

    public ResponseEntity<List<Profile>> getAllProfiles() {
        log.info("inside ProfileService, getting profiles");
        List<Profile> profiles = profileRepository.findAll();
        if (profiles.isEmpty()) throw new ResourceNotFoundException("no Profiles found");
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    public ResponseEntity<Profile> getProfileById(int id) {
        log.info("inside ProfileService, getting profile having id : {}", id);
        Optional<Profile> profile = profileRepository.findById(id);
        if (profile.isEmpty()) throw new ResourceNotFoundException("no profile found for id : " + id);
        return new ResponseEntity<>(profile.get(), HttpStatus.OK);
    }

    public ResponseEntity<Profile> getProfileByUsername(String name) {
        log.info("inside ProfileService, getting profile having name : {}", name);
        Optional<Profile> profile = profileRepository.findByUsername(name);
        if (profile.isEmpty()) throw new ResourceNotFoundException("no profile found for name : " + name);

        return new ResponseEntity<>(profile.get(), HttpStatus.OK);
    }

    public ResponseEntity<Profile> updateProfileById(int id, ProfileDto profileDto) {
        log.info("inside ProfileService, updating profile having id : {} and profile : {}", id, profileDto);
        Optional<Profile> p = profileRepository.findById(id);

        if (p.isEmpty()) throw new ResourceNotFoundException("profile not found for id : " + id);
        Profile oldProfile = p.get();

        if (profileDto.getUsername() != null && !profileDto.getUsername().isEmpty()) {
            oldProfile.setUsername(profileDto.getUsername());
        }
        if (profileDto.getProfilePicUrl() != null) {
            oldProfile.setProfilePicUrl(profileDto.getProfilePicUrl());
        }
        if (profileDto.getBio() != null) {
            oldProfile.setBio(profileDto.getBio());
        }
        if (profileDto.getPassword() != null && !profileDto.getPassword().isEmpty()) {
            oldProfile.setPassword(profileDto.getPassword());
        }

        Profile updatedProfile = profileRepository.save(oldProfile);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    public ResponseEntity<Integer> updateUsernameAndPasswordById(int id, String username, String password) {
        int i = profileRepository.updateUsernameAndPasswordById(id, username, password);
        if (i == 0) throw new ResourceNotFoundException("user not founde for id : " + id);

        return new ResponseEntity<>(i, HttpStatus.OK);
    }

    public ResponseEntity<Profile> deleteProfileById(int id) {
        log.info("inside ProfileService, deleting profile having id : {}", id);
        Optional<Profile> profile = profileRepository.findById(id);
        if (profile.isEmpty()) throw new ResourceNotFoundException("profile not found for id : " + id);

        profileRepository.deleteById(id);
        return new ResponseEntity<>(profile.get(), HttpStatus.OK);
    }

}
