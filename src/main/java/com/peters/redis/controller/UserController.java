package com.peters.redis.controller;

import com.peters.redis.dto.UserRequest;
import com.peters.redis.dto.UserResponse;
import com.peters.redis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest request){
        return userService.saveUser(request);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> users;
        users = userService.fetchAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/user/{id}")
//    @Cacheable(value = "userCache", key="#id") //Used annotation for caching
    public UserResponse getUserById(@PathVariable("id") Long id){
        return userService.fetUserById(id).getBody();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long id, @RequestBody UserRequest request){
        return userService.updateUser(id, request);
    }

}
