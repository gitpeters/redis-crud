package com.peters.redis.service;

import com.peters.redis.dto.UserRequest;
import com.peters.redis.dto.UserResponse;
import com.peters.redis.entity.User;
import com.peters.redis.repository.Dao;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final Dao repository;
    @Override
    public ResponseEntity<UserResponse> saveUser(UserRequest request) {
        return repository.saveUser(request);
    }

    @Override
    public List<UserResponse> fetchAllUsers() {
        List<UserResponse> users;
        users = repository.fetchAllUsers();
        return users;
    }

    @Override
    public ResponseEntity<UserResponse> fetUserById(Long id) {
        return repository.fetchUserById(id);
    }

    @Override
    public ResponseEntity<UserResponse> deleteUser(Long id) {
        return repository.deleteUser(id);
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(Long id, UserRequest request) {
        return repository.updateUser(id, request);
    }
}
