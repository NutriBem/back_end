package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Patient {

    @Id
    @Column(length = 11)
    private String cpf;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 60)
    private String email;

    @Column(nullable = false, length = 40)
    private String password;

    @Column(nullable = false, length = 11)
    private String telephone;
}
