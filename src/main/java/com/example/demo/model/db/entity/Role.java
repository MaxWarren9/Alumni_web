package com.example.demo.model.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }


    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<Alumni> users = new HashSet<>();

}
