package com.ashar.profileManager.controller;

import com.ashar.profileManager.dto.UserDto;
import com.ashar.profileManager.entity.User;
import com.ashar.profileManager.response.Response;
import com.ashar.profileManager.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-manager")

public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<Response<User>> addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @PostMapping("/user/all")
    public ResponseEntity<Response<List<User>>> addAllUsers(@RequestBody @Size(min = 1) List< @Valid UserDto> userDtos) {
        return userService.addAllUsers(userDtos);
    }

    @GetMapping("/user/all")
    public ResponseEntity<Response<List<User>>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Response<User>> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/user/name/{name}")
    public ResponseEntity<Response<User>> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Response<User>> updateUserById(@PathVariable int id, @RequestBody @Valid UserDto userDto) {
        return userService.updateUserById(id, userDto);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Response<User>> deleteUserById(@PathVariable int id) {
        return userService.deleteUserById(id);
    }

}
