package com.define.domain;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;

@Data
public class Person implements Serializable {
    private String name;
    private String job;
    private int age;
    @Transient
    private transient String privacy;
}
