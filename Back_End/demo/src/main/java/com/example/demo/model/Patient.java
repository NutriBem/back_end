package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "id")
@EqualsAndHashCode(callSuper = false)
@DiscriminatorValue("PATIENT")
public class Patient extends Person {

    @Column(length = 11, unique = true)
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    
}


