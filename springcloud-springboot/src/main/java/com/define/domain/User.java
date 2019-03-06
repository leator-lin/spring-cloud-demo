package com.define.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "user")
public class User {
     @Id
     @GeneratedValue
     private Integer Id;
     @Column
     private String name;
}