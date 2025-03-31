package com.example.demo.model.security;

import com.example.demo.model.db.entity.Alumni;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.stream.Collectors;

@Getter

public class CustomUserDetails implements UserDetails {
    private final Alumni alumni;

    public CustomUserDetails(Alumni alumni) {
        this.alumni = alumni;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return alumni.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return alumni.getPassword();
    }

    @Override
    public String getUsername() {
        return alumni.getUsername();
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
        return true;
    }
}