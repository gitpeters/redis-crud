package com.peters.redis.repository;

import com.peters.redis.dto.UserRequest;
import com.peters.redis.dto.UserResponse;
import com.peters.redis.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DaoImpl implements Dao{

    private final RedisTemplate redisTemplate;
    private final String KEY = "USER";

    @Override
    public ResponseEntity<UserResponse> saveUser(UserRequest request) {
       try{
           User user = User.builder()
                   .id(request.getId())
                   .firstName(request.getFirstName())
                   .lastName(request.getLastName())
                   .email(request.getEmail())
                   .age(request.getAge())
                   .build();
           User existingUser = (User) redisTemplate.opsForHash().get(KEY, request.getId().toString());
           if (existingUser != null && request.getId().equals(existingUser.getId())) {
               return ResponseEntity.badRequest().body(new UserResponse("Failed", "This user already exists"));
           }

           User userWithSameEmail = null;
           for (Object value : redisTemplate.opsForHash().values(KEY)) {
               User storedUser = (User) value;
               if (storedUser.getEmail().equals(request.getEmail())) {
                   userWithSameEmail = storedUser;
                   break;
               }
           }
           if (userWithSameEmail != null) {
               return ResponseEntity.badRequest().body(new UserResponse("Failed", "User with the same email already exists"));
           }

           redisTemplate.opsForHash().put(KEY, request.getId().toString(), user);
           return ResponseEntity.ok().body(new UserResponse("Successful", "Successfully save user"));
       }catch (Exception e){
           e.printStackTrace();
           return ResponseEntity.badRequest().body(new UserResponse("Failed", "Could not save user"));
       }

    }

    @Override
    public List<UserResponse> fetchAllUsers() {
        List<User> users = redisTemplate.opsForHash().values(KEY);
        return users.stream().map((this::mapToUserResponse)).collect(Collectors.toList());

    }

    @Override
    public ResponseEntity<UserResponse> fetchUserById(Long id) {
        try{
            User user = (User) redisTemplate.opsForHash().get(KEY, id.toString());
            UserResponse response = UserResponse.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .age(user.getAge())
                    .build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new UserResponse("Failed", "No user found for this id::"+id));
        }

    }

    @Override
    public ResponseEntity<UserResponse> deleteUser(Long id) {
        try{
            redisTemplate.opsForHash().delete(KEY, id.toString());

            return ResponseEntity.ok().body(new UserResponse("Successful", "User deleted successfully"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new UserResponse("Failed", "No user found for this id::"+id));
        }
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(Long id, UserRequest request) {
        try{

            User existingUser = (User) redisTemplate.opsForHash().get(KEY, id.toString());
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            existingUser.setAge(request.getAge());
            existingUser.setEmail(request.getEmail());
            redisTemplate.opsForHash().put(KEY, existingUser.getId().toString(), existingUser);
            return ResponseEntity.ok().body(new UserResponse("Successful", "Updated user's record successfully"));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new UserResponse("Failed", "No user found for this id::"+id));
        }
    }


    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .age(user.getAge())
                .build();
    }


}
