package com.example.demo.model.db.entity;

import com.example.demo.enums.AlumniStatus;
import com.example.demo.enums.Gender;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "alumni")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Alumni implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "email")
    String email;
    private String username;
    @Column(name = "password")
    String password;

    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;
    @Column(name = "graduation_year")
    Integer graduationYear;
    @Column(name = "gender")
    Gender gender;
    @Column(name = "status")
    AlumniStatus status;

    @OneToMany
    @JsonManagedReference(value = "alumni_specialization")
    List<Specialization> specializations;
    @ElementCollection(fetch = FetchType.EAGER)
    Set<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return status == AlumniStatus.CREATED;
    }

}