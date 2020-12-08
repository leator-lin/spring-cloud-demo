package com.define.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String name;
    private Integer age;
    private String password;
    private String email;

    public UserDTO(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}