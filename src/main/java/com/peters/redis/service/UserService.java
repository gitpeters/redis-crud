package com.peters.redis.service;

import com.peters.redis.dto.UserRequest;
import com.peters.redis.dto.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    ResponseEntity<UserResponse> saveUser(UserRequest request);

    List<UserResponse> fetchAllUsers();

    ResponseEntity<UserResponse> fetUserById(Long id);

    ResponseEntity<UserResponse> deleteUser(Long id);

    ResponseEntity<UserResponse> updateUser(Long id, UserRequest request);
}
