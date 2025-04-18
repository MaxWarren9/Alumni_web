package com.example.demo.service;

import com.example.demo.model.db.repository.AlumniRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AlumniDetailsService implements UserDetailsService {
    private final AlumniRepo alumniRepo;

    public AlumniDetailsService(AlumniRepo alumniRepo) {
        this.alumniRepo = alumniRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return alumniRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
