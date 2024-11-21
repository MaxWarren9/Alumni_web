package com.example.demo.model.db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.persistence.Id;

@Getter
@Setter
@Table(name = "specializations")
@Entity
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonManagedReference(value = "alumni_specialization")
    private Alumni alumni;
}