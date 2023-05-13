package com.peters.redis.repository;

import com.peters.redis.dto.UserRequest;
import com.peters.redis.dto.UserResponse;
import com.peters.redis.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface Dao {
    ResponseEntity<UserResponse> saveUser(UserRequest request);

    List<UserResponse> fetchAllUsers();

    ResponseEntity<UserResponse> fetchUserById(Long id);

    ResponseEntity<UserResponse> deleteUser(Long id);

    ResponseEntity<UserResponse> updateUser(Long id, UserRequest request);
}
