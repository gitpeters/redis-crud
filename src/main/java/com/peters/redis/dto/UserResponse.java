package com.peters.redis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private String status;
    private String message;

    public UserResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
