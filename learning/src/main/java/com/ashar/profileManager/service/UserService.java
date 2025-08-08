package com.ashar.profileManager.service;

import com.ashar.profileManager.dto.UserDto;
import com.ashar.profileManager.entity.User;
import com.ashar.profileManager.exception.ResourceNotFoundException;
import com.ashar.profileManager.repository.UserRepository;
import com.ashar.profileManager.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final Response response;
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository,Response response){
        this.userRepository = userRepository;
        this.response = response;
    }

    public ResponseEntity<Response<User>> addUser(UserDto userDto){
        User user=new User();

        log.info("current user Id : {}",user.getId());
        user.setName(userDto.getName());
        user.setAddress(userDto.getAddress());
        user.setDateOfBirth(userDto.getDateOfBirth());

        User savedUser = userRepository.save(user);
        log.info("user created in database : {}",savedUser);
        response.setResponse("created");
        response.setMessage("user created successfully");
        response.setObject(savedUser);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<Response<List<User>>> addAllUsers(List<UserDto> userDtos) {
        List<User> list = new ArrayList<>();

        User user = new User();

        for(UserDto userDto: userDtos){
            user.setName(userDto.getName());
            user.setAddress(userDto.getAddress());
            user.setDateOfBirth(userDto.getDateOfBirth());

            User savedUser = userRepository.save(user);
            list.add(savedUser);
        }

        response.setResponse("created");
        response.setMessage("all users created successfully");
        response.setObject(list);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    public ResponseEntity<Response<List<User>>> getAllUsers(){
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) throw new ResourceNotFoundException("No users in database");

        log.info("fetched all users from database");
        response.setResponse("fetched");
        response.setMessage("All users fetched successfully");
        response.setObject(users);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<Response<User>> getUserById(int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new ResourceNotFoundException("user not found for id : "+id);

        log.info("user fetched from database : {}",user.get());
        response.setResponse("fetched");
        response.setMessage("user fetched successfully");
        response.setObject(user.get());

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<Response<User>> getUserByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        if(user.isEmpty()) throw new ResourceNotFoundException("user not found for username : "+name);

        log.info("user fetched from database : {}",user.get());
        response.setResponse("fetched");
        response.setMessage("user fetched successfully");
        response.setObject(user.get());

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<Response<User>> updateUserById(int id, UserDto userDto){
        Optional<User> u = userRepository.findById(id);
        if(u.isEmpty()) throw new ResourceNotFoundException("user not found for id : "+id);
        User oldUser = u.get();

        if(userDto.getName() != null && !userDto.getName().isEmpty() && !userDto.getName().equals(oldUser.getName())){
            oldUser.setName(userDto.getName());
        }
        if(userDto.getDateOfBirth() != null && !userDto.getDateOfBirth().equals(oldUser.getDateOfBirth())){
            oldUser.setDateOfBirth(userDto.getDateOfBirth());
        }
        if(userDto.getAddress() != null && !userDto.getAddress().isEmpty() && !userDto.getAddress().equals(oldUser.getAddress())){
            oldUser.setAddress(userDto.getAddress());
        }

        User savedUser = userRepository.save(oldUser);
        log.info("User updated in database : {}",oldUser);
        response.setResponse("updated");
        response.setMessage("user updated successfully");
        response.setObject(oldUser);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<Response<User>> deleteUserById(int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new ResourceNotFoundException("user not found for id : "+id);

        userRepository.deleteById(id);
        log.info("user deleted from database : {}",user.get());
        response.setResponse("deleted");
        response.setMessage("user deleted successfully");
        response.setObject(user.get());

        return new ResponseEntity<>(response,HttpStatus.OK);
    }



}
