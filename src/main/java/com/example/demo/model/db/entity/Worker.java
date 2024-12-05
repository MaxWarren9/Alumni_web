package com.example.demo.model.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "worker")
public class Worker extends User{
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;
    @Column(nullable = false)
    String department;
    @Column(name = "started_work_year")
    Integer startedWorkYear;
    @Column(name = "ended_work_year")
    Integer endedWorkYear;
}
