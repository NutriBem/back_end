package com.example.demo.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Table(name = "tb_recepcionist")
@Entity
@Data
public class Recepcionist extends Person{


    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 60, unique = true)
    private String email;

    @Column(nullable = false, length = 40)
    private String password;//Hash com BCrypt aplicado

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")//pesquisar qual o numero maximo
    @Column(nullable = false, length = 11)
    private String telephone;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

}

