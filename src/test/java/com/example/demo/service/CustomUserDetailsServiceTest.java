package com.example.demo.service;

import com.example.demo.model.db.entity.Alumni;
import com.example.demo.model.db.repository.AlumniRepo;
import com.example.demo.model.db.repository.RoleRepo;
import com.example.demo.model.security.CustomUserDetails;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomUserDetailsServiceTest {

    private AlumniRepo alumniRepo;
    private RoleRepo roleRepo;
    private CustomUserDetailsService userDetailsService;

    @Before
    public void setUp() {
        alumniRepo = mock(AlumniRepo.class);
        roleRepo = mock(RoleRepo.class);
        userDetailsService = new CustomUserDetailsService(alumniRepo, roleRepo);
    }

    @Test
    public void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        // given
        Alumni alumni = new Alumni();
        alumni.setId(1L);
        alumni.setUsername("john");
        alumni.setPassword("pass123");

        when(alumniRepo.findByUsername("john")).thenReturn(Optional.of(alumni));

        UserDetails userDetails = userDetailsService.loadUserByUsername("john");

        assertTrue(userDetails instanceof CustomUserDetails);
        assertEquals("john", userDetails.getUsername());
        assertEquals("pass123", userDetails.getPassword());

        verify(alumniRepo, times(1)).findByUsername("john");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_shouldThrow_whenUserNotFound() {
        when(alumniRepo.findByUsername("missingUser")).thenReturn(Optional.empty());

        userDetailsService.loadUserByUsername("missingUser");
    }
}