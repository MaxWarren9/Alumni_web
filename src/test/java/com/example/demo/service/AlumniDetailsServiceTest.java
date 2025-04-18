package com.example.demo.service;

import com.example.demo.model.db.entity.Alumni;
import com.example.demo.model.db.repository.AlumniRepo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AlumniDetailsServiceTest {
    private AlumniRepo alumniRepo;
    private AlumniDetailsService alumniDetailsService;

    @Before
    public void setUp() {
        alumniRepo = mock(AlumniRepo.class);
        alumniDetailsService = new AlumniDetailsService(alumniRepo);
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        String username = "testUser";
        Alumni alumni = new Alumni();
        alumni.setUsername(username);
        when(alumniRepo.findByUsername(username)).thenReturn(Optional.of(alumni));

        var result = alumniDetailsService.loadUserByUsername(username);

        assertEquals(alumni, result);
        verify(alumniRepo, times(1)).findByUsername(username);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsername_UserNotFound() {
        String username = "nonexistentUser";
        when(alumniRepo.findByUsername(username)).thenReturn(Optional.empty());

        alumniDetailsService.loadUserByUsername(username);
    }
}