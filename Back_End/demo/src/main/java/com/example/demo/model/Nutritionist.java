package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "id")
@EqualsAndHashCode(callSuper = false)
@DiscriminatorValue("NUTRITIONIST")
public class Nutritionist extends Person {
    
    @Column(nullable = false, unique = true)
    private String crm;

    @Transient
    List<Agenda> agendaList;
}
