package com.example.demo.service;

import com.example.demo.model.db.entity.Alumni;
import com.example.demo.model.db.repository.AlumniRepo;
import com.example.demo.model.db.repository.RoleRepo;
import com.example.demo.model.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AlumniRepo alumniRepo;
    private final RoleRepo roleRepo;

    public CustomUserDetailsService(AlumniRepo alumniRepo, RoleRepo roleRepo) {
        this.alumniRepo = alumniRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Alumni alumni = alumniRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(alumni);
    }
}
