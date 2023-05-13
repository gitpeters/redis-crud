package com.peters.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User{
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}
