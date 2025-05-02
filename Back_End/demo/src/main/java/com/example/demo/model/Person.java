package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class Person {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 60)
    private String email;

    @Column(nullable = false, length = 40)
    private String password;

    @Column(nullable = false, length = 11)
    private String telephone;
}
