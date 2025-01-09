package com.example.demo.model.db.entity;

import com.example.demo.enums.Gender;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "alumni")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Alumni extends User implements UserDetails {
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;
    @Column(name = "graduation_year")
    Integer graduationYear;
    @Column(name = "gender")
    Gender gender;

    @OneToMany
    @JsonManagedReference(value = "alumni_specialization")
    List<Specialization> specializations;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "alumni_roles",
            joinColumns = @JoinColumn(name = "alumni_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles = new HashSet<>();
}