package com.define.dto;

import lombok.Data;

@Data
public class User {
    private String id;
    private String username;
    private String password;
    private String address;
    private String email;
}